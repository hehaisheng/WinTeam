package com.shawnway.nav.app.wtw.tool;

import android.content.Context;
import android.content.res.AssetManager;

import com.shawnway.nav.app.wtw.bean.CityModel;
import com.shawnway.nav.app.wtw.bean.DistrictModel;
import com.shawnway.nav.app.wtw.bean.ProvinceModel;
import com.shawnway.nav.app.wtw.service.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2016/8/12.
 */
public class CityUtils {

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;

    public String[] getmProvinceDatas() {
        return mProvinceDatas;
    }

    public void setmProvinceDatas(String[] mProvinceDatas) {
        this.mProvinceDatas = mProvinceDatas;
    }

    public Map<String, String[]> getmCitisDatasMap() {
        return mCitisDatasMap;
    }

    public void setmCitisDatasMap(Map<String, String[]> mCitisDatasMap) {
        this.mCitisDatasMap = mCitisDatasMap;
    }

    public String getmCurrentProviceName() {
        return mCurrentProviceName;
    }

    public void setmCurrentProviceName(String mCurrentProviceName) {
        this.mCurrentProviceName = mCurrentProviceName;
    }

    public String getmCurrentCityName() {
        return mCurrentCityName;
    }

    public void setmCurrentCityName(String mCurrentCityName) {
        this.mCurrentCityName = mCurrentCityName;
    }

    private List<ProvinceModel> provinceList = null;
    /**
     * 解析省市区的XML数据
     */
    public void  initProvinceDatas(Context context)
    {
        AssetManager asset =context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();

                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
        }
    }
}
