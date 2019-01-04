package com.example.android.sixcalendar.utils;

import android.text.TextUtils;

import com.example.android.sixcalendar.entries.LaoHuangLi;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * Created by jackie on 2018/12/29.
 */

public class ParseXmlUtil {

    public static LaoHuangLi parseLaoHuangLiXml(String info) {
        if (TextUtils.isEmpty(info)) return null;
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(info);
            Element root = document.getRootElement();
            List<Element> listElement = root.elements("body");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
