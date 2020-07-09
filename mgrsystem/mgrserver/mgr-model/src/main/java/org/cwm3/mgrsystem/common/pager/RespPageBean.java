package org.cwm3.mgrsystem.common.pager;

import java.util.List;

/**

 * @作者 cwm3

 * @时间 2019-10-29 7:42
 */
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
