package com.zhidian.test;

/**
 * 这是一个 递归问题，求一个长度为n的字符串的全排列的方法为：

 n[0..n.len-1]全排列的计算方法为：将n[0]位置的字符分别和n[1..n.len-1]的每一个字符串交换，n[0]和交换后的n[1..n.len - 1]的全排列进行组合。我们将字符串{s}的全排列表示为{s}，那么对于abc来说，其全排列{abc}，就等于是a + {bc}、b + {ac}，c + {ba}。

 以此类推，n[1..n.len - 1]的全排列，则是将n[1]分别和n[2..n.len - 1]的每一个字符串交换，再求出交换后的n[2..len - 1]的全排列，递归结束的条件为n[i..n.len - 1]只有一个字符，例如，bc的全排列为b + {c}、c + {b}，而{c}和{b}的全排列只有一种，因此递归结束，这时候就可以打印出结果。
 * Created by 江俊升 on 2018/2/27.
 */
public class TestString {

    static void permutationStr(char p[], int depth, int length){
        if (depth == length) {
            System.out.println(p);
            return;
        }
        char c;
        for (int i = depth; i < length; i++){
            c = p[depth]; p[depth] = p[i]; p[i] = c;
            permutationStr(p, depth+1, length);
            c = p[depth]; p[depth] = p[i]; p[i] = c;
        }
    }
    public static void main(String[] args) {
        char[] source = "abc".toCharArray();
        permutationStr(source, 0, source.length);
    }
}
