public class Token {
        public static void main(String[] args) { // 创建一个令牌桶，最大容量为100，每毫秒补充1个令牌
            TokenBucket tokenBucket = new TokenBucket(100, 1);        // 模拟请求处理
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    while (true) {
                        if (tokenBucket.tryConsume(50)) {
                            System.out.println(Thread.currentThread().getName() + " 执行请求");                    }
                        else {
                            System.out.println(Thread.currentThread().getName() + " 请求被限流");                    }
                        try {                    // 每个线程等待100毫秒后尝试再次请求
                            Thread.sleep(100);                    }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, "线程" + i).start();
            }
        }
}
