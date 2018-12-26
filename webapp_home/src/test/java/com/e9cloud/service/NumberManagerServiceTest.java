package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.mybatis.domain.AppNumber;
import com.e9cloud.mybatis.domain.AppNumberRest;
import com.e9cloud.mybatis.service.NumberManagerService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
public class NumberManagerServiceTest extends BaseTest {
    @Autowired
    private NumberManagerService numberManagerService;

    /** 添加号码 **/
    @Test
    public void addAppNumbers(){
        List<AppNumber> list = new ArrayList();
        AppNumber appNumber7 = new AppNumber();
        appNumber7.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber7.setNumber("4001000113-4001000120");
        appNumber7.setNumberType("02");
        AppNumber appNumber6 = new AppNumber();
        appNumber6.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber6.setNumber("4001000112");
        appNumber6.setNumberType("01");
        AppNumber appNumber5 = new AppNumber();
        appNumber5.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber5.setNumber("4001000111");
        appNumber5.setNumberType("01");
        AppNumber appNumber4 = new AppNumber();
        appNumber4.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber4.setNumber("4001000104-4001000110");
        appNumber4.setNumberType("02");
        AppNumber appNumber3 = new AppNumber();
        appNumber3.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber3.setNumber("4001000103");
        appNumber3.setNumberType("01");
        AppNumber appNumber2 = new AppNumber();
        appNumber2.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber2.setNumber("4001000102");
        appNumber2.setNumberType("01");
        AppNumber appNumber1 = new AppNumber();
        appNumber1.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");
        appNumber1.setNumber("4001000101");
        appNumber1.setNumberType("01");

        list.add(appNumber1);
        list.add(appNumber2);
        list.add(appNumber3);
        list.add(appNumber4);
        list.add(appNumber5);
        list.add(appNumber6);
        list.add(appNumber7);

        numberManagerService.addAppNumbers(list);
    }

    /** 查询号码 **/
    @Test
    public void selectAppNumber(){
        AppNumber appNumber = new AppNumber();
        appNumber.setAppid("b1d0dc9be9f14fe5ad511d617cef64ee");

        List<AppNumber> list = numberManagerService.findAppNumberList(appNumber);
        for(AppNumber appNum:list){
            System.out.println("---------------------"
                    +appNum.getAppid()+"---------------------"
                    +appNum.getNumber()+"---------------------"
                    +appNum.getNumberType()+"---------------------"
                    +appNum.getNumberStatus()+"---------------------"
                    +appNum.getRemark()+"---------------------"
            );
        }
    }

    /** 审核号码 **/
    @Test
    public void checkedAppNumber(){
        AppNumber appNumber = new AppNumber();
        appNumber.setId(new Long(65));
        appNumber.setNumberStatus("01");
        appNumber.setRemark("信息正确，审核通过");
        //appNumber.setNumberStatus("02");
        //appNumber.setRemark("信息错误，审核不通过");
        numberManagerService.updateAppNumberById(appNumber);

        if("01".equals(appNumber.getNumberStatus())) {
            List<AppNumber> appNumberList = numberManagerService.findAppNumberList(appNumber);
            List<AppNumberRest> appNumberRestList = new ArrayList<>();
            if ("01".equals(appNumberList.get(0).getNumberType())) {
                AppNumberRest appNumberRest = new AppNumberRest();
                appNumberRest.setNumberId(appNumberList.get(0).getId());
                appNumberRest.setAppId(appNumberList.get(0).getAppid());
                appNumberRest.setNumber(appNumberList.get(0).getNumber());
                appNumberRestList.add(appNumberRest);
            } else if ("02".equals(appNumberList.get(0).getNumberType())) {
                String[] numArray = appNumberList.get(0).getNumber().split("-");

                String strNumStart = numArray[0].substring(0,numArray[0].length()-3);
                int intNumStart = Integer.parseInt(numArray[0].substring(numArray[0].length()-3,numArray[0].length()));
                int intNumEnd = Integer.parseInt(numArray[1].substring(numArray[1].length()-3,numArray[1].length()));

                String diffStr = "";
                //将号码切割成数组
                String[] diffNumArray = numArray[0].split("");
                if(diffNumArray.length<=3) {
                    for (int i = 0; i < diffNumArray.length; i++) {
                        if ("0".equals(diffNumArray[i])) {
                            diffStr += "0";
                        } else {
                            break;
                        }
                    }
                }

                int diffNum = Math.abs(intNumEnd - intNumStart);
                if(diffNum>0 && diffNum<=999) {
                    int startNum = (intNumStart - intNumEnd) < 0 ? intNumStart : intNumEnd;
                    for (int i = 0; i <= diffNum; i++) {
                        AppNumberRest appNumberRest = new AppNumberRest();
                        appNumberRest.setNumberId(appNumberList.get(0).getId());
                        appNumberRest.setAppId(appNumberList.get(0).getAppid());
                        //如果号码长度小于等于三位就直接拼接“0”字符串
                        if(numArray[0].length() <= 3){
                            appNumberRest.setNumber(diffStr+String.valueOf(startNum + i));
                        }else{
                            appNumberRest.setNumber(strNumStart+String.valueOf(startNum + i));
                        }
                        appNumberRestList.add(appNumberRest);
                    }
                }
            }
            numberManagerService.addAppNumberRest(appNumberRestList);
        }
    }

    /** 删除号码 **/
    @Test
    public void deleteAppNumber(){
        Long[] ids = {new Long(15)};
        numberManagerService.deleteAppNumberRest(ids);
        numberManagerService.deleteAppNumbers(ids);
    }

    /** 检查号码重复 **/
    @Test
    public void checkNumberRepeat(){
        Map<String, Object> map = new HashMap<>();
        map.put("appid","b1d0dc9be9f14fe5ad511d617cef64ee");
        String[] numbers = {"4001000106","4001000102","4001000119","4001000108","4001000130"};
        map.put("numbers",numbers);
        List<AppNumberRest> appNumberRestsList = numberManagerService.selectAppNumberRestByNumbers(map);
        for (AppNumberRest appNumberRest:appNumberRestsList) {
            System.out.println("----------------------------------------"
                +appNumberRest.getNumberId()+"----------------------------------------"
                +appNumberRest.getAppId()+"----------------------------------------"
                +appNumberRest.getNumber()+"----------------------------------------"
            );
        }
    }


}
