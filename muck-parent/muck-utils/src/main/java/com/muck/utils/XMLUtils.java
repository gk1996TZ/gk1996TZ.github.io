package com.muck.utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLUtils {

	public static void main(String[] args)throws Exception {

		// 1、解析器
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();

		// 2、用解析器解析文件
		MyHandler3 hander = new MyHandler3();
		
		StringReader reader = new StringReader("");
		parser.parse(new InputSource(reader), hander);

		// 3、我通过handlder就可以取出来封装好的数据
		List<Student> students = hander.getStudents();
		for (Student student : students) {
			System.out.println(student);
		}
	}
}
class MyHandler3 extends DefaultHandler{
	
	private String currentTagName;  // 这个属性用来定义当前解析到的开始标签
	
	private List<Student> students = new ArrayList<Student>(); // 用来存储xml中封装好的Student对象
	
	private Student student;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		currentTagName = qName;  // Device , currentTagName = Device
		
		if("Device".equals(currentTagName)){
			student = new Student();
			
			for(int i = 0 ; i < attributes.getLength() ; i++){
				String attrName = attributes.getQName(i); // sex
				String attrValue = attributes.getValue(attrName);				// 男
				
				if("sex".equals(attrName)){
					student.setSex(attrValue);
				}
				else if("weight".equals(attrName)){
					student.setWeight(Integer.parseInt(attrValue));
				}
			}
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if("no".equals(currentTagName)){
			student.setNo(new String(ch,start,length));
		}
		else if("name".equals(currentTagName)){
			student.setName(new String(ch,start,length));
		}
		else if("age".equals(currentTagName)){
			student.setAge(Integer.parseInt(new String(ch,start,length)));
		}
		else if("address".equals(currentTagName)){
			student.setAddress(new String(ch,start,length));
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		currentTagName = null;
		
		if("student".equals(qName)){
			students.add(student);
		}
	}
	
	public List<Student> getStudents(){
		return students;
	}
}

// 实体javabean
class Student{
	
	private String no;
	
	private String name;
	
	private int age;
	
	private String address;
	
	private String sex;
	
	private int weight;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + ", age=" + age + ", address=" + address + ", sex=" + sex
				+ ", weight=" + weight + "]";
	}
}
