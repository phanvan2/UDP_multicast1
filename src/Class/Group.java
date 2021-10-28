package Class;

import java.util.Vector;

public class Group {
	String nameGroup ; 
	Vector<String> thanhVien = new Vector<String>();
	public Group(String nameGroup, String thanhVien) {
		super();
		this.nameGroup = nameGroup;
		this.thanhVien.add(thanhVien);
	}
	public String getNameGroup() {
		return nameGroup;
	}
	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}
	public Vector<String> getThanhVien() {
		return thanhVien;
	}
	public void setThanhVien(Vector<String> thanhVien) {
		this.thanhVien = thanhVien;
	}
	public void addThanhVien(String member) {
		this.thanhVien.add(member);
	}
	
	
	
}
