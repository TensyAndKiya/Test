package com.clei.Y2019.M06.D10;

public class DropMiddleCommitTest {
    public static void main(String[] args) {
        //假设我做了几次commit 并且push上去了。
        //commit1 commit2 commit3
        //发现中间的commit2是不需要的 而且 commit3不依赖于commit2的话。。。我要抛弃commit
        //commit1
        //commit3
        // git revert commit_id  这里要删掉commit2的话用commit2的id
        //之后会出现编辑器。。输入commit message后保存退出
        //再push的话就行了
        //git远程有这次revert的 commit  也有commit2的commit
        //有痕迹的回退。。还行吧。。
        //commit11
        //commit33
    }
}
