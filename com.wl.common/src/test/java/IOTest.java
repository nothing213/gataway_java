import com.sun.management.OperatingSystemMXBean;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 系统监控
 */
public class IOTest {

    public static void init() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {

                SystemInfo systemInfo = new SystemInfo();

                OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
                // 椎内存使用情况
                MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

                // 初始的总内存
                long initTotalMemorySize = memoryUsage.getInit();
                // 最大可用内存
                long maxMemorySize = memoryUsage.getMax();
                // 已使用的内存
                long usedMemorySize = memoryUsage.getUsed();

                // 操作系统
                String osName = System.getProperty("os.name");
                // 总的物理内存
                String totalMemorySize = new DecimalFormat("#.##")
                        .format(osmxb.getTotalPhysicalMemorySize() / 1024.0 / 1024 / 1024) ;
                // 剩余的物理内存
                String freePhysicalMemorySize = new DecimalFormat("#.##")
                        .format(osmxb.getFreePhysicalMemorySize() / 1024.0 / 1024 / 1024) ;
                // 已使用的物理内存
                String usedMemory = new DecimalFormat("#.##").format(
                        (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize()) / 1024.0 / 1024 / 1024)
                        + "G";
                // 获得线程总数
                ThreadGroup parentThread;
                for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
                        .getParent() != null; parentThread = parentThread.getParent()) {

                }

                int totalThread = parentThread.activeCount();

                // 磁盘使用情况
                File[] files = File.listRoots();
                for (File file : files) {
                    String total = new DecimalFormat("#.#").format(file.getTotalSpace() * 1.0 / 1024 / 1024 / 1024)
                            + "G";
                    String free = new DecimalFormat("#.#").format(file.getFreeSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String un = new DecimalFormat("#.#").format(file.getUsableSpace() * 1.0 / 1024 / 1024 / 1024) + "G";
                    String path = file.getPath();
                    System.err.println(path + "总:" + total + ",可用空间:" + un + ",空闲空间:" + free);
                    System.err.println("=============================================");
                }
                Double total = 0D;
                Double free = 0D;
                Double un = 0D;
                for(int i = 0;i < files.length;i++)
                {
                    Double temple_total = Double.valueOf(new DecimalFormat("#.#").format(files[i].getTotalSpace() * 1.0 / 1024 / 1024 / 1024));
                    total = temple_total+total;
                    Double temple_free = Double.valueOf(new DecimalFormat("#.#").format(files[i].getFreeSpace() * 1.0 / 1024 / 1024 / 1024));
                    free = free+temple_free;
//                    Double temple_un = Double.valueOf(new DecimalFormat("#.#").format(files[i].getUsableSpace() * 1.0 / 1024 / 1024 / 1024));
                }
                Double Io =1- free / total;
                Double Cpu = 0D;
                Double memory = 0D;

                System.err.println("操作系统:" + osName);
                System.err.println("程序启动时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime())));
                System.err.println("pid:" + System.getProperty("PID"));
                System.err.println("cpu核数:" + Runtime.getRuntime().availableProcessors());
                Cpu =  printlnCpuInfo(systemInfo);
                Cpu = 1 - Cpu / 100;
                System.err.println("JAVA_HOME:" + System.getProperty("java.home"));
                System.err.println("JAVA_VERSION:" + System.getProperty("java.version"));
                System.err.println("USER_HOME:" + System.getProperty("user.home"));
                System.err.println("USER_NAME:" + System.getProperty("user.name"));
                System.err.println("初始的总内存(JVM):"
                        + new DecimalFormat("#.#").format(initTotalMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println(
                        "最大可用内存(JVM):" + new DecimalFormat("#.#").format(maxMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println(
                        "已使用的内存(JVM):" + new DecimalFormat("#.#").format(usedMemorySize * 1.0 / 1024 / 1024) + "M");
                System.err.println("总的物理内存:" + totalMemorySize);
                System.err
                        .println("总的物理内存:"
                                + new DecimalFormat("#.##").format(
                                systemInfo.getHardware().getMemory().getTotal() * 1.0 / 1024 / 1024 / 1024)
                                + "M");
                System.err.println("剩余的物理内存:" + freePhysicalMemorySize);
                System.err
                        .println("剩余的物理内存:"
                                + new DecimalFormat("#.##").format(
                                systemInfo.getHardware().getMemory().getAvailable() * 1.0 / 1024 / 1024 / 1024)
                                + "M");
                System.err.println("已使用的物理内存:" + usedMemory);
                System.err.println("已使用的物理内存:"
                        + new DecimalFormat("#.##").format((systemInfo.getHardware().getMemory().getTotal()
                        - systemInfo.getHardware().getMemory().getAvailable()) * 1.0 / 1024 / 1024 / 1024)
                        + "M");

                Double total_memory = Double.valueOf(totalMemorySize);
//                String temple_total_memory  = totalMemorySize.substring(0,totalMemorySize.length()-1);
                Double rest_memory = Double.valueOf(freePhysicalMemorySize);
                memory =1- rest_memory / total_memory;
                System.err.println("总线程数:" + totalThread);
                System.err.println("===========================");
                DecimalFormat df = new DecimalFormat("#0.00");
                Io = Double.valueOf(df.format(Io));
                Cpu = Double.valueOf(df.format(Cpu));
                memory =Double.valueOf(df.format(memory));
                System.out.println("总磁盘："+total+"----"+"可用磁盘："+free+"------"+"io占用用率是:"+Io);
                System.out.println("cpu占用率是："+ Cpu);
                System.out.println("内存占用率是:"+memory);

                Double result = (Cpu * 0.5+Io*0.25+memory*0.25) ;
                System.out.println("最终负载值为："+ result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 60, TimeUnit.SECONDS);

    }

    /**
     * 打印 CPU 信息
     *
     * @param systemInfo
     */
    public static Double printlnCpuInfo(SystemInfo systemInfo) throws InterruptedException {
        Double Cpu = 0D;
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()]
                - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()]
                - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()]
                - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()]
                - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()]
                - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()]
                - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()]
                - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()]
                - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        System.err.println("cpu核数:" + processor.getLogicalProcessorCount());
        System.err.println("cpu系统使用率:" + new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        System.err.println("cpu用户使用率:" + new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        System.err.println("cpu当前等待率:" + new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        System.err.println("cpu当前空闲率:" + new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu));
        System.err.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadBetweenTicks() * 100);
        System.err.format("CPU load: %.1f%% (OS MXBean)%n", processor.getSystemCpuLoad() * 100);
        String temple_cpu = new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu);
        temple_cpu = temple_cpu.substring(0,temple_cpu.length()-1);
        Cpu = Double.valueOf(temple_cpu);
        return Cpu;
    }

    public static void main(String[] args) {

        init();
    }
}