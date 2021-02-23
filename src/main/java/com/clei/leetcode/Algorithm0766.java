package com.clei.leetcode;

import com.clei.utils.PrintUtil;

/**
 * 托普利茨矩阵
 * 给你一个 m x n 的矩阵 matrix 。如果这个矩阵是托普利茨矩阵，返回 true ；否则，返回 false 。
 * <p>
 * 如果矩阵上每一条由左上到右下的对角线上的元素都相同，那么这个矩阵是 托普利茨矩阵 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * 输入：matrix = [[1,2,3,4],[5,1,2,3],[9,5,1,2]]
 * 输出：true
 * 解释：
 * 在上述矩阵中, 其对角线为:
 * "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]"。
 * 各条对角线上的所有元素均相同, 因此答案是 True 。
 * 示例 2：
 * <p>
 * <p>
 * 输入：matrix = [[1,2],[2,2]]
 * 输出：false
 * 解释：
 * 对角线 "[1, 2]" 上的元素不同。
 * <p>
 * <p>
 * 提示：
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 20
 * 0 <= matrix[i][j] <= 99
 * <p>
 * <p>
 * 进阶：
 * <p>
 * 如果矩阵存储在磁盘上，并且内存有限，以至于一次最多只能将矩阵的一行加载到内存中，该怎么办？
 * 如果矩阵太大，以至于一次只能将不完整的一行加载到内存中，该怎么办？
 *
 * @author KIyA
 */
public class Algorithm0766 {

    public static void main(String[] args) {
        // int[][] arr = new int[][]{{1, 2, 3, 4}, {5, 1, 2, 3}, {9, 5, 1, 2}};
        // int[][] arr = new int[][]{{1, 2}, {2,2}};
        int[][] arr = new int[][]{{36, 59, 71, 15, 26, 82, 87}, {56, 36, 59, 71, 15, 26, 82}, {15, 0, 36, 59, 71, 15, 26}};
        PrintUtil.log(isToeplitzMatrix(arr));
        // {36,59,71,15,26,82,87}
        // {56,36,59,71,15,26,82}
        // {15,0,36,59,71,15,26}
    }

    public static boolean isToeplitzMatrix(int[][] matrix) {
        int x = matrix.length;
        if (x < 2) {
            return true;
        }
        int y = matrix[0].length;
        if (y < 2) {
            return true;
        }
        // 第一行 除了最后一个数字，全部判断
        for (int i = 0; i < y - 1; i++) {
            int num = matrix[0][i];
            int xx = 1, yy = i + 1;
            while (xx < x && yy < y) {
                if (matrix[xx][yy] != num) {
                    return false;
                }
                xx++;
                yy++;
            }
        }
        // 除了第一行和最后一行的 只判断第一个数字
        for (int i = 1; i < x - 1; i++) {
            int num = matrix[i][0];
            int xx = i + 1, yy = 1;
            while (xx < x && yy < y) {
                if (matrix[xx][yy] != num) {
                    return false;
                }
                xx++;
                yy++;
            }
        }
        return true;
    }
}
