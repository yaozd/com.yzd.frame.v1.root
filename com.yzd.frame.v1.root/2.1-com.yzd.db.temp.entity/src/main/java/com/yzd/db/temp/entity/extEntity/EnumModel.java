package com.yzd.db.temp.entity.extEntity;

/**
 * Created by Jack on 2016/12/9.
 */
public class EnumModel {
    /**
     * 是否有效状态
     */
    public enum IsDel{
        有效(1),无效(2);
        private int value;
        private IsDel(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 注册(来源)平台
     */
    public enum Platform{
        pc(1),
        mobile(2),
        other(3);
        private int value;
        private Platform(int val){
            this.value=val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 注册(来源)平台分类
     */
    public enum PlatformType{
        pc(1),
        android(2),
        iphone(3),
        mac(4),
        weixin(5),
        other(6);
        private int value;
        private PlatformType(int val){
            this.value=val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 渠道
     */
    public enum Channel{
        me(0),
        other(1);
        private int value;
        private Channel(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 渠道分类
     */
    public enum ChannelType{
        me(0),
        other(1);
        private int value;
        private ChannelType(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

}
