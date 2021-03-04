package com.ezm.utils;

import org.hyperic.sigar.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取系统CPU、内存、硬盘使用情况
 *
 * @author yanzy
 * @date 2019/1/16 9:45
 */
public class SystemInfoUtil {



    /**
     * 获取内存使用率
     *
     * @return java.lang.String
     * @author yanzy
     * @version 1.0
     * @date 2019/1/16 16:56
     */
    public static  String getMemoryUsage() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 获取总内存数
        Long total = mem.getTotal() / 1024L;
        // 获取已使用内存数
        Long used = mem.getUsed() / 1024L;
        // 取到百分比
        Long percentile = total / 100;
//        return "内存使用率：" + used / percentile + "%";
        return String.valueOf(used / percentile);
    }

    /**
     * 获取cpu使用率
     *
     * @return java.lang.String
     * @author yanzy
     * @version 1.0
     * @date 2019/1/16 16:57
     */
    public static String getCpuUsage() throws SigarException {
        Sigar sigar = new Sigar();
        return  CpuPerc.format(sigar.getCpuPerc().getCombined());
    }

    /**
     * 获取磁盘使用率
     *
     * @return java.util.List<java.lang.String>
     * @author yanzy
     * @version 1.0
     * @date 2019/1/16 16:57
     */
    public List<String> getDiskUsage() throws Exception {
        Sigar sigar = new Sigar();
        List<String> stringList = new ArrayList<>();
        FileSystem[] fslist = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            FileSystem fileSystem = fslist[i];
            FileSystemUsage usage = sigar.getFileSystemUsage(fileSystem.getDirName());
            double usePercent = usage.getUsePercent() * 100D;
            stringList.add(fileSystem.getDevName() + "磁盘的使用率:" + (int) usePercent + "%");
        }
        return stringList;
    }

    /**
     * 获取当前磁盘IO的使用情况
     *
     * @return java.lang.String
     * @author yanzy
     * @version 1.0
     * @date 2019/1/17 18:31
     */
    public String getDiskIoUsage() throws Exception {
        // 获取当前每秒的读写量
        long tempTotal = getTotalByte();
        Thread.sleep(1000);
        long total = getTotalByte();

        // 字节转成kb
        double cha = (double) (total - tempTotal) / 1024;
        // 转成MB,保留1位小数
        BigDecimal bigDecimal = new BigDecimal(cha / 1024);
        double value = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return "磁盘IO的使用情况：" + value + " MB/S";
    }

    /**
     * 获取当前磁盘IO的读写数量
     *
     * @return long
     * @author yanzy
     * @version 1.0
     * @date 2019/1/17 16:11
     */
    public long getTotalByte() {
        Sigar sigar = new Sigar();
        long totalByte = 0;
        try {
            FileSystem[] fslist = sigar.getFileSystemList();
            for (int i = 0; i < fslist.length; i++) {
                if (fslist[i].getType() == 2) {
                    FileSystemUsage usage = sigar.getFileSystemUsage(fslist[i].getDirName());
                    totalByte += usage.getDiskReadBytes();
                    totalByte += usage.getDiskWriteBytes();
                }
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return totalByte;
    }

    /**
     * 获取当前项目的磁盘使用情况
     *
     * @return java.lang.String
     * @author yanzy
     * @version 1.0
     * @date 2019/1/21 10:34
     */
    public String path() throws Exception {
        String path = getClass().getResource("/").getFile();
        if (path.contains("/") && path.contains(":")) {
            path = path.replace("/", "").substring(0, path.indexOf(":"));
        }

        for (String disk : this.getDiskUsage()) {
            if (disk.contains(path) || disk.contains("rootfs")) {
                return "当前服务所在磁盘的使用率：" + disk;
            }
        }
        return "未找到相应的磁盘";
    }

    /**
     * Linux下获取磁盘IO使用率
     *
     * @return float
     * @author yanzy
     * @version 1.0
     * @date 2019/1/17 10:07
     */
    public float getHdIOpPercent() {
        System.out.println("开始收集磁盘IO使用率");
        float ioUsage = 0.0f;
        Process pro = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            String command = "iostat -d -x";
            pro = runtime.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                if (++count >= 4) {
                    String[] temp = line.split("\\s+");
                    if (temp.length > 1) {
                        float util = Float.parseFloat(temp[temp.length - 1]);
                        ioUsage = (ioUsage > util) ? ioUsage : util;
                    }
                }
            }
            if (ioUsage > 0) {
                System.out.println("本节点磁盘IO使用率为: " + ioUsage);
                ioUsage /= 100;
            }
            in.close();
            pro.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println("IoUsage发生InstantiationException. " + e.getMessage());
            System.out.println(sw.toString());
        }
        return ioUsage;
    }
}
