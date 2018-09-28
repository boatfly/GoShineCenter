package com.goshine.gscenter.common.isc.util;

import com.goshine.gscenter.common.model.Staff;
import com.goshine.gscenter.gskit.time.DateFormatUtil;
import com.sgcc.isc.core.orm.organization.BusinessOrganization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SynchroISC {
    public static List<Staff> getStaff(List<Map<String, Object>> list) {

        List<Staff> listStaff = new ArrayList<Staff>();
        for (Map<String, Object> map : list) {
            Staff staff = new Staff();
            staff.setEmpNo((String) map.get("id"));
            staff.setStaffNo((String) map.get("id"));
            staff.setName((String) map.get("name"));
            // 单位
            staff.setOrgIdIsc((String) map.get("baseOrgId"));
            // 同步时间
            //staff.setCreateTime(DateHelper.convertToString(new Date(),DateHelper.YEAR_MONTH_DAY_H24_MIN_SEC));
            staff.setCreateTime(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT_ON_SECOND,new Date()));
            listStaff.add(staff);
        }

        return listStaff;
    }

    public List getOrg(List<BusinessOrganization> list) {
        List<Staff> listStaff = new ArrayList<Staff>();
        Staff staff;
        for (BusinessOrganization u : list) {
            u.getCode();
            u.getId();
            u.getName();
            u.getParentId();
            u.getState();
            // 同步时间
            // staff.setCreateTime(DateHelper.convertToString(new
            // Date(),DateHelper.YEAR_MONTH_DAY_H24_MIN_SEC));

        }

        return listStaff;
    }
}
