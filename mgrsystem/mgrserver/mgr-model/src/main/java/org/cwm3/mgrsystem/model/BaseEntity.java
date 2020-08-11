package org.cwm3.mgrsystem.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;



import java.util.Date;

/**
 * @Classname BaseEntity
 * @Author chengweiming
 * @Date 2020/8/11 21:40
 */
@Data
public abstract class BaseEntity<T extends Model> extends Model {
    /**
     * 创建日期 - 现在时表示主动创建
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改时间 - 过去分词表示被动更新
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
