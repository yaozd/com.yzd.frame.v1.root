drop table if exists tbl_test_data;

/*==============================================================*/
/* Table: tbl_test_data                                         */
/* 阿里巴巴Java开发规范手册.pdf                                 */
/*==============================================================*/
create table tbl_test_data
(
   id                   bigint  not null auto_increment,
   name                 varchar(30),
   password             varchar(100),
   gmt_create           datetime comment '创建时间',
   gmt_modified         datetime comment '修改时间',
   gmt_is_cached_updated int  comment '是否缓存被更新',
   gmt_is_deleted       int  comment '是否被删除',
   primary key (id)
);

