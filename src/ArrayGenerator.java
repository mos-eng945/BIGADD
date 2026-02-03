public class ArrayGenerator {
    // 生成指定大小的随机数组
    public  int[] generateHugeArray(int size) {
        System.out.println("正在生成 " + size + " 个元素的大数组...");
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int)(Math.random() * 1000000); // 0-999999的随机数
        }
        System.out.println("数组生成完成！");
        return array;
    }
}
