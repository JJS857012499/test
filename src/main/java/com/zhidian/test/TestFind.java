package com.zhidian.test;

import java.util.HashMap;

/**
 * Created by 江俊升 on 2019/4/1.
 */
public class TestFind {


    public static void main(String[] args) {
        /**
         * 给定一个整数值nums和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标
         * eg.
         * 给定nums =[2, 7, 11, 15] ,target = 9
         * 因为 nums[0] + nums[1] = 2 +7 =9
         * 所以返回 [0,1]
         */

        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] result = find(nums, target);
        System.out.println("result：" + result[0] + result[1]);
    }

    private static int[] find(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums == null || nums.length < 1) {
            return result;
        }

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int a = nums[i];
            int b = target - a;
            if (hashMap.containsKey(b)) {
                result[0] = hashMap.get(b);
                result[1] = i;
                break;
            }
            hashMap.put(a, i);
        }
        return result;
    }


}
