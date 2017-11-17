package com.module.sys.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;  
import java.util.Iterator;  
import java.util.Map;  
import java.util.Set;


  
/** 
 * 敏感词过滤 
 *  
 * @author dxm 
 *  
 */  
@SuppressWarnings("rawtypes")  
public class SensitiveWordService {  
  
    private Map sensitiveWordMap = null;  
  
    // 最小匹配规则  
    public static int minMatchTYpe = 1;  
  
    // 最大匹配规则  
    public static int maxMatchType = 2;  
  
    // 单例  
    private static SensitiveWordService inst = null;  
  
    /** 
     * 构造函数，初始化敏感词库 
     */  
    private SensitiveWordService() {  
        sensitiveWordMap = new SensitiveWordInitNew().initKeyWord();  
    }  
  
    /** 
     * 获取单例 
     *  
     * @return 
     */  
    public static SensitiveWordService getInstance() {  
        if (null == inst) {  
            inst = new SensitiveWordService();  
        }  
        return inst;  
    }  
  
    /** 
     * 判断文字是否包含敏感字符 
     *  
     * @param txt 
     * @param matchType 
     * @return 
     */  
    public boolean isContaintSensitiveWord(String txt, int matchType) {  
        boolean flag = false;  
        for (int i = 0; i < txt.length(); i++) {  
  
            // 判断是否包含敏感字符  
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType);  
  
            // 大于0存在，返回true  
            if (matchFlag > 0) {  
                flag = true;  
            }  
        }  
        return flag;  
    }  
  
    /** 
     * 获取文字中的敏感词 
     *  
     * @param txt 
     * @param matchType 
     * @return 
     */  
    public Set<String> getSensitiveWord(String txt, int matchType) {  
        Set<String> sensitiveWordList = new HashSet<String>();  
  
        for (int i = 0; i < txt.length(); i++) {  
  
            // 判断是否包含敏感字符  
            int length = CheckSensitiveWord(txt, i, matchType);  
  
            // 存在,加入list中  
            if (length > 0) {  
                sensitiveWordList.add(txt.substring(i, i + length));  
  
                // 减1的原因，是因为for会自增  
                i = i + length - 1;  
            }  
        }  
  
        return sensitiveWordList;  
    }  
  
    /** 
     * 替换敏感字字符 
     *  
     * @param txt 
     * @param matchType 
     * @param replaceChar 
     * @return 
     */  
    public String replaceSensitiveWord(String txt, int matchType, String replaceChar) {  
  
        String resultTxt = txt;  
  
        // 获取所有的敏感词  
        Set<String> set = getSensitiveWord(txt, matchType);  
        Iterator<String> iterator = set.iterator();  
        String word = null;  
        String replaceString = null;  
        while (iterator.hasNext()) {  
            word = iterator.next();  
            replaceString = getReplaceChars(replaceChar, word.length());  
            resultTxt = resultTxt.replaceAll(word, replaceString);  
        }  
  
        return resultTxt;  
    }  
  
    /** 
     * 获取替换字符串 
     *  
     * @param replaceChar 
     * @param length 
     * @return 
     */  
    private String getReplaceChars(String replaceChar, int length) {  
        String resultReplace = replaceChar;  
        for (int i = 1; i < length; i++) {  
            resultReplace += replaceChar;  
        }  
  
        return resultReplace;  
    }  
  
    /** 
     * 检查文字中是否包含敏感字符，检查规则如下：<br> 
     * 如果存在，则返回敏感词字符的长度，不存在返回0 
     *  
     * @param txt 
     * @param beginIndex 
     * @param matchType 
     * @return 
     */  
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {  
  
        // 敏感词结束标识位：用于敏感词只有1位的情况  
        boolean flag = false;  
  
        // 匹配标识数默认为0  
        int matchFlag = 0;  
        Map nowMap = sensitiveWordMap;  
        for (int i = beginIndex; i < txt.length(); i++) {  
            char word = txt.charAt(i);  
  
            // 获取指定key  
            nowMap = (Map) nowMap.get(word);  
  
            // 存在，则判断是否为最后一个  
            if (nowMap != null) {  
  
                // 找到相应key，匹配标识+1  
                matchFlag++;  
  
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数  
                if ("1".equals(nowMap.get("isEnd"))) {  
  
                    // 结束标志位为true  
                    flag = true;  
  
                    // 最小规则，直接返回,最大规则还需继续查找  
                    if (SensitiveWordService.minMatchTYpe == matchType) {  
                        break;  
                    }  
                }  
            }  
  
            // 不存在，直接返回  
            else {  
                break;  
            }  
        }  
  
        // 长度必须大于等于1，为词  
        if (matchFlag < 2 || !flag) {  
            matchFlag = 0;  
        }  
        return matchFlag;  
    }  
  
    public static void main(String[] args) {  
  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");  
  
        SensitiveWordService  filter = SensitiveWordService.getInstance();  
        String txt = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。" + "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，" + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";  
        //txt = "法轮大法";  
        System.out.println(sdf.format(new Date()));  
        String hou = filter.replaceSensitiveWord(txt, 1, "*");  
        System.out.println(sdf.format(new Date()));  
        System.out.println("替换前的文字为：" + txt);  
        System.out.println("替换后的文字为：" + hou);  
    }  
    public class SensitiveWordInitNew {  
    	  
        // 字符编码  
        private String ENCODING = "UTF-8";  
      
        /** 
         * 初始化敏感字库 
         *  
         * @return 
         */  
        public Map initKeyWord() {  
      
            // 读取敏感词库  
            Set<String> wordSet = readSensitiveWordFile();  
      
            // 将敏感词库加入到HashMap中  
            return addSensitiveWordToHashMap(wordSet);  
        }  
      
        /** 
         * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br> 
         * 中 = { isEnd = 0 国 = {<br> 
         * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 
         * 1 } } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 
         * } } } } 
         *  
         */  
        private Map addSensitiveWordToHashMap(Set<String> wordSet) {  
      
            // 初始化敏感词容器，减少扩容操作  
            Map wordMap = new HashMap(wordSet.size());  
      
            for (String word : wordSet) {  
                Map nowMap = wordMap;  
                for (int i = 0; i < word.length(); i++) {  
      
                    // 转换成char型  
                    char keyChar = word.charAt(i);  
      
                    // 获取  
                    Object tempMap = nowMap.get(keyChar);  
      
                    // 如果存在该key，直接赋值  
                    if (tempMap != null) {  
                        nowMap = (Map) tempMap;  
                    }  
      
                    // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个  
                    else {  
      
                        // 设置标志位  
                        Map<String, String> newMap = new HashMap<String, String>();  
                        newMap.put("isEnd", "0");  
      
                        // 添加到集合  
                        nowMap.put(keyChar, newMap);  
                        nowMap = newMap;  
                    }  
      
                    // 最后一个  
                    if (i == word.length() - 1) {  
                        nowMap.put("isEnd", "1");  
                    }  
                }  
            }  
           //System.out.println("wordMap="+wordMap.toString());
            return wordMap;  
        }  
      
        /** 
         * 读取敏感词库中的内容，将内容添加到set集合中 
         *  
         * @return 
         * @throws Exception 
         */  
        private Set<String> readSensitiveWordFile() {  
      
            Set<String> wordSet = null;  
      
            // 读取文件  
           // String app = System.getProperty("user.dir");  
            String filepath="src/main/resources/hotelsensitive.txt";
            
            File file = new File(filepath);  
            System.out.println("filepath="+filepath);
            try {  
      
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);  
      
                // 文件流是否存在  
                if (file.isFile() && file.exists()) {  
      
                    wordSet = new HashSet<String>();  
                    StringBuffer sb = new StringBuffer();  
                    BufferedReader bufferedReader = new BufferedReader(read);  
                    String txt = null;  
      
                    // 读取文件，将文件内容放入到set中  
                    while ((txt = bufferedReader.readLine()) != null) {  
                        sb.append(txt);  
                    }  
                    bufferedReader.close();  
      
                    String str = sb.toString();  
                   // System.out.println("str="+str);
                    String[] ss = str.split(",");  
                    System.out.println("str="+ss.length);
                    
                    for (String s : ss) {  
                        wordSet.add(s);  
                        
                    }  
                }  
      
                // 关闭文件流  
                read.close();  
      
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
      
            return wordSet;  
        }  
    }  
}