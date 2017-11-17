package com.module.sys.utils;

public enum QuestionTypeEnum {
	
        TYPE11("单选题", 11), TYPE12("多选题", 12), TYPE21("名词解释", 21), TYPE22("简答题", 22), TYPE23("案例分析", 23),TYPE24("翻译题", 24),TYPE25("填空题", 25);
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private QuestionTypeEnum(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static String getName(int index) {
            for ( QuestionTypeEnum c :  QuestionTypeEnum.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

