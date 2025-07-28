# Display.java - จัดการการแสดงข้อความและหน้าต่าง

## ภาพรวม
Display เป็นคลาส utility ที่มีเมธอด static สำหรับจัดการการแสดงข้อความและหน้าต่างพิเศษ เช่น dialog box และหน้าต่าง About

## เมธอดสำคัญ

### showMessage(Component parent, String title, String msg, int type)
แสดงข้อความในรูปแบบ Dialog:
- กำหนดสีและฟอนต์ของ Dialog ให้สอดคล้องกับธีม
- รองรับ message type ต่างๆ (INFO, WARNING, ERROR)
- รีเซ็ต UI Manager หลังใช้งาน

#### การปรับแต่ง UI
- พื้นหลัง: ใช้สีจาก Colors.BG_MAIN
- ข้อความ: ใช้สีและฟอนต์จาก Settings
- ปุ่ม: ปรับสีและเส้นขอบให้เข้ากับธีม

### showAbout(Component parent)
แสดงหน้าต่าง About ของแอปพลิเคชัน:
- สร้าง JDialog แบบ modal
- แสดงข้อมูลทีมพัฒนา
- ไม่มี title bar (undecorated)
- ขนาดคงที่ 850x480 พิกเซล

#### โครงสร้างหน้าต่าง About
1. **Header**: ชื่อทีมและคำอธิบายโปรเจค
2. **Team Section**: ข้อมูลสมาชิกทีม 3 คน
3. **Footer**: ปุ่มปิดหน้าต่าง

### makeMemberBox(String imgPath, String name, String id, String job)
สร้างกล่องแสดงข้อมูลสมาชิกทีม:
- แสดงรูปภาพสมาชิก (120x120 พิกเซล)
- แสดงชื่อ รหัสนิสิต และตำแหน่ง
- มีเส้นขอบและพื้นหลังสี
- จัดเรียงแนวตั้ง

#### ข้อมูลที่แสดง
- รูปภาพสมาชิก
- ชื่อ-นามสกุล (ฟอนต์หนา)
- รหัสนิสิต (ข้อความสีอ่อน)
- ตำแหน่งในทีม (สีม่วง)

### makeImage(String imgPath)
โหลดและปรับขนาดรูปภาพ:
- โหลดรูปจาก path ที่กำหนด
- ปรับขนาดเป็น 120x120 พิกเซล
- ใช้ SCALE_SMOOTH สำหรับคุณภาพที่ดี
- แสดง emoji 👤 หากโหลดรูปไม่สำเร็จ

## การจัดการสีและธีม

### การปรับแต่ง UIManager
ทั้งสองเมธอดหลักจะปรับแต่ง UIManager เพื่อ:
- ให้ Dialog มีสีสอดคล้องกับแอปพลิเคชัน
- ใช้ฟอนต์ที่กำหนดใน Settings
- รีเซ็ตค่ากลับเป็น null หลังใช้งาน

### สีที่ใช้
- พื้นหลังหลัก: Colors.BG_MAIN
- พื้นหลังแผง: Colors.BG_PANEL  
- ข้อความหลัก: Colors.TEXT_DARK
- ข้อความรอง: Colors.TEXT_LIGHT
- สีเน้น: Colors.BLUE, Colors.PURPLE

## การใช้งาน

### แสดงข้อความทั่วไป
```java
Display.showMessage(parent, "ข้อผิดพลาด", "ไม่สามารถโหลดไฟล์ได้", JOptionPane.ERROR_MESSAGE);
```

### แสดงหน้าต่าง About
```java
Display.showAbout(this);
```

## ความสัมพันธ์กับคลาสอื่น
- ใช้ค่าคงที่จาก Settings (ฟอนต์, ข้อความ, ข้อมูลสมาชิก)
- ใช้สีจาก Colors
- ใช้ ButtonHelper สำหรับสร้างปุ่มปิด
- ถูกเรียกใช้โดย MainApp และ GridUI

## คุณสมบัติพิเศษ
- รองรับการแสดงรูปภาพแบบ fallback
- ปรับขนาดรูปภาพอัตโนมัติ
- จัดการ modal dialog อย่างถูกต้อง
- รีเซ็ต UI settings หลังใช้งาน