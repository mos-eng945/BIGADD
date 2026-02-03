public class ParallelSumCalculator {

    private final int threadCount;
    // 工作线程内部类
    private  class SumWorker extends Thread {
        private final int[] array;
        private final int start;
        private final int end;
        private long partialSum;     //部分和

        public SumWorker(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            partialSum = 0;
            for (int i = start; i < end; i++) {
                partialSum += array[i];
            }
        }

        public long getPartialSum() {
            return partialSum;
        }
    }


    public ParallelSumCalculator(int threadCount) {
        if (threadCount <= 0) {
            System.out.println("线程数必须大于0，当前: " + threadCount);
        }
        this.threadCount = threadCount;
        System.out.println("  线程数: " + threadCount);
    }
    // 并行求和主方法
    public long parallelSum(int[] array) {
        int threadNum = threadCount;
        int size = array.length / threadNum;//每个组的长度
        SumWorker[] workers = new SumWorker[threadNum];//创建一个存sumworker的数组长为线程数
        // 创建并启动所有线程
        int i = 0;
        while (i < threadNum) {
            int start = i * size;//分块
            int end = start + size;
            if (i == threadNum - 1) {
                end = array.length;
            }//避免截断除法可能有余数

            workers[i] = new SumWorker(array, start, end);
            workers[i].start();//线程开始
            i++;
        }

        // 等待所有线程完成
        int j = 0;
        while (j < threadNum) {
            try {
                workers[j].join();  // 等待线程完成
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            j++;
        }

        // 汇总结果
        long total = 0;
        int k = 0;
        while (k < threadNum) {
            total += workers[k].getPartialSum();
            k++;
        }

        return total;
    }


    // 单线程版本
    public long singleThreadSum(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum = sum + array[i];
        }
        return sum;
    }


    // 获取实际线程数
    public int getThreadCount() {
        return threadCount;
    }
}
