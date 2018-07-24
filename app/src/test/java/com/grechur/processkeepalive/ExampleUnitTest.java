package com.grechur.processkeepalive;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    /**
     *
     * @param arr  数组
     * @param row  行
     * @param col  列
     * @param level 层数
     * @param count 计数
     */
    public void travel(int [][] arr, int row, int col, int level, int count) {
        //递归出口
        if (row - 2 * level == 0 || col - 2 * level == 0) {
            return;
        }

        if (row - 2 * level == 1) {
            for (int i = level; i < col -level; ++i) {
                arr[level][i] = count++;
            }
            return;
        }

        if (col - 2 * level == 1) {
            for (int i = level; i < row -level; ++i) {
                arr[i][level] = count++;
            }
            return;
        }

        /**
         * 遍历四条边
         */
        for (int i = level; i < col - level; ++i) {
            arr[level][i] = count++;
        }

        for (int i = level + 1; i < row - 1 - level; ++i) {
            arr[i][col - 1 - level] = count++;
        }

        for (int i = col - 1 - level; i >= level; --i){
            arr[row - 1- level][i] = count++;
        }

        for (int i = row - 2 - level; i >= level+1; --i){
            arr[i][level] = count++;
        }
        // 递归
        travel(arr, row, col, level + 1, count);
    }


    /**
     * 打印
     * @param arr
     * @param row
     * @param col
     */
    public void print(int[][] arr, int row, int col){
        for (int i = 0; i < row; ++i){
            for (int j = 0; j < col; ++j){
                System.out.print(arr[i][j] + "    ");
            }
            System.out.println();
        }
    }

    @Test
    public void testTravel() {
        int [][] arr = new int[5][6];
        travel(arr, 5, 6, 0, 0);
        print(arr, 5, 6);
    }

}