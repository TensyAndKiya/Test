package com.clei.algorithm.other;

import com.clei.entity.Goods;
import com.clei.utils.PrintUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态规划
 * 小偷背包
 *
 * @author KIyA
 */
public class DynamicProgramming1 {

    public static void main(String[] args) {
        List<Goods> goodsList = new ArrayList<>(4);
        goodsList.add(new Goods("吉他", 1, 1500));
        goodsList.add(new Goods("音响", 4, 3000));
        goodsList.add(new Goods("笔记本电脑", 3, 2000));
        goodsList.add(new Goods("华为手机", 1, 2000));
        // n千克容量的背包最好装些什么商品
        Bag bag = getGoods(goodsList, 4);
        PrintUtil.log("商品些：{}", bag.getGoodsList().stream().map(Goods::getName).collect(Collectors.joining(",")));
        PrintUtil.log("总重量：{}", bag.getWeight());
        PrintUtil.log("总价格：{}", bag.getPrice());
    }

    private static Bag getGoods(List<Goods> goodsList, int bagCapacity) {
        if (CollectionUtils.isEmpty(goodsList) || bagCapacity <= 0) {
            throw new RuntimeException("你真的是温柔 善良 贤淑 美丽 可爱");
        }
        // 最轻的
        float minWeight = goodsList.stream().map(Goods::getWeight).min(Float::compareTo).get();
        // 这里商品的重量最多保留两位小数 所以乘以100可以转为整数
        int n = 100;
        int num = (int) (n * minWeight);
        // 获取公约数
        int gcd = Euclid.getGCD(num, bagCapacity * n);
        // 基本单位大小
        float unit = gcd * 1.0f / n;
        // 商品的种类数目是动态的
        // 所以这里二维数组的大小也要动态获取
        int y = bagCapacity * n / gcd;
        int x = goodsList.size();
        // 创建数组
        Bag[][] arr = new Bag[x][y];
        // 第一行
        Goods goods = goodsList.get(0);
        Bag bag = goods.getWeight() <= unit ? new Bag(goods) : new Bag();
        if (goods.getWeight() <= unit) {
            for (int j = 0; j < y; j++) {
                arr[0][j] = bag;
            }
        }
        // 第n行 n > 1
        for (int i = 1; i < x; i++) {
            Goods g = goodsList.get(i);
            for (int j = 0; j < y; j++) {
                float price1 = arr[i - 1][j].getPrice();
                float price2 = g.getPrice();
                int tempY = j - (int) (g.getWeight() / unit);
                // 背包能装下当前商品和剩余容量的最大价值商品
                if (tempY > -1 && price2 + arr[i - 1][tempY].getPrice() > price1) {
                    arr[i][j] = new Bag(arr[i - 1][tempY], g);
                    // 背包只能装下当前商品
                } else if (tempY == -1 && price2 > price1) {
                    arr[i][j] = new Bag(g);
                }
                // 没装上 默认装上格的
                if (null == arr[i][j]) {
                    arr[i][j] = arr[i - 1][j];
                }
            }
        }
        return arr[x - 1][y - 1];
    }

    /**
     * 背包
     */
    private static class Bag {

        /**
         * 装了的商品
         */
        List<Goods> goodsList = new ArrayList<>();

        /**
         * 装的商品的总重量 单位：千克
         */
        private float weight;

        /**
         * 装的商品的总价格 单位：元
         */
        private float price;

        public Bag(Goods goods) {
            this.goodsList.add(goods);
            this.weight = goods.getWeight();
            this.price = goods.getPrice();
        }

        public Bag(Bag bag, Goods goods) {
            this.goodsList.addAll(bag.getGoodsList());
            this.goodsList.add(goods);
            this.weight = bag.getWeight() + goods.getWeight();
            this.price = bag.getPrice() + goods.getPrice();
        }

        public Bag() {
        }

        public List<Goods> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<Goods> goodsList) {
            this.goodsList = goodsList;
        }

        public float getWeight() {
            return weight;
        }

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
