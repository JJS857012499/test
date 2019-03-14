package com.zhidian.test;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 江俊升 on 2019/3/14.
 */
public class TestHuiwen {

    public static void main(String[] args) {
        String test = "aab";
        System.out.println("是否回文：" + isHuiwen1(test));
        System.out.println("是否回文：" + isHuiwen2(test));
        Solution solution = new Solution();
        List<List<String>> partition = solution.partition(test);
        partition.stream().forEach(System.out::println);

    }

    /**
     * 这个方法有问题 只有为双数才可以，并且能允许多个回文
     * abc ×
     * abbc √
     * abbaaa √
     *
     * @param string
     * @return
     */
    public static boolean isHuiwen1(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }

        Stack<Character> stack = new Stack();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (stack.isEmpty()) {
                stack.push(c);
            } else if (stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }


    public static boolean isHuiwen2(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }

        char[] chars = string.toCharArray();
        int length = chars.length;
        for (int i = 0; i < length / 2; i++) {
            if (chars[i] != chars[length -1 - i]) {
                return false;
            }
        }
        return true;
    }


}

/**
 * How to do?
 * Input: "aab"
 * Output:
 * [
 *   ["aa","b"],
 *   ["a","a","b"]
 * ]
 *
 * eg.
 *  "aab"
 *  ① [0,0] "a" 是回文，然后[0,1] aa ,然后[0,2] "aab"
 *  ② 在运行第①步骤的同时，剩下的子串“ab”, 也做递归调用(穷举法)
 *
 *  [0,0] =>  a、 ab
 *  [0,1] =>  aa、 b
 *  [0,2] =>  aab
 *
 */
class Solution {

    List<List<String>> resultList;

    ArrayList<String> currList;

    public List<List<String>> partition(String s) {
        resultList = new ArrayList<>();
        currList = new ArrayList<>();
        backTrack(s,0);
        return resultList;
    }

    public void backTrack(String s, int l){

        //完成了一组
        if(currList.size() > 0 && l>=s.length()) {
            resultList.add(currList);
            currList =  new ArrayList<>(); //为下组准备
        }

        for(int i = l; i < s.length(); i++){
            if(isHuiwen(s, l, i)){
                if( l == i)
                    currList.add(Character.toString(s.charAt(i)));
                else
                    currList.add(s.substring(l, i + 1));
                backTrack(s,i + 1);
            }
        }
    }
    public boolean isHuiwen(String str, int l, int r){
        if ( l == r)
            return true;

        while( l < r){
            if (str.charAt(l) != str.charAt(r))
                return false;
            l++;
            r--;
        }
        return true;
    }
}
