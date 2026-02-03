public class Main {
    public static void main(String[] args) {
        System.out.println("大数组并行求和程序启动");
        System.out.println("-----------------------------------");

        // 生成测试数据
        int size = 500000000;  // 5亿
        ArrayGenerator a =new ArrayGenerator();
        int[] hugeArray = a.generateHugeArray(size);
        // 测试不同线程数的性能
        int[] testThread = {4,6,8,16,24,32};
        testWithThreadCounts(hugeArray, testThread);
    }

    public static void testWithThreadCounts(int[] array, int[] threadCounts) {
        System.out.println("\n开始多线程性能对比测试...");
        //先单线程
        ParallelSumCalculator b = new ParallelSumCalculator(1);
        System.out.println("\n开始单线程计算...");
        long Start1 = System.currentTimeMillis();//计时
        long Result1 = b.singleThreadSum(array);//单线程的和
        long End1 = System.currentTimeMillis();
        long Time1 = End1 - Start1;
        System.out.printf("值: %d | 耗时: %dms\n", Result1, Time1);
        System.out.println("-----------------------------------");

        // 测试不同线程数
        for (int i = 0; i < threadCounts.length; i++) {
            int threadCount = threadCounts[i];

            System.out.println("\n测试 " + threadCount + " 线程...");

            // 创建计算器
            ParallelSumCalculator c = new ParallelSumCalculator(threadCount);

            // 计时开始
            long start = System.currentTimeMillis();
            long result = c.parallelSum(array);
            // 计时结束
            long end = System.currentTimeMillis();
            long time = end - start;

            // 验证结果正确性
            String check = (result == Result1) ? "成功" : "失败";

            System.out.println(check + " " + threadCount + "线程: " + result +
                    " | 耗时: " + time + "ms" );
        }


    }
}