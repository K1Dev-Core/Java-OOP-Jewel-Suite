# Jewel Suite - Gas Distribution Simulation System

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="60" alt="java logo"  />
  <h3>ระบบจำลองการกระจายตัวของแก๊สใต้ดิน</h3>
  <p><strong>เวอร์ชั่น 1.0.0</strong></p>
</div>

## 📋 ภาพรวม

Jewel Suite เป็นแอปพลิเคชัน Java GUI สำหรับจำลองการกระจายตัวของแก๊สในพื้นที่ใต้ดิน โดยสามารถโหลดข้อมูลความลึกจากไฟล์ คำนวณปริมาตรแก๊ส 

## 🚀 การติดตั้งและรัน

### ความต้องการของระบบ
- Java JDK 8 หรือสูงกว่า
- flatlaf-3.4.1 หรืิอสูงกว่า

## 💻 การใช้งานใน IntelliJ IDEA

1. เปิดโปรเจคใน IntelliJ IDEA
2. **คลิกขวา** ที่โฟลเดอร์ `lib` ใน Project Explorer
3. เลือก **"Add as Library..."**
4. กด **OK** เพื่อเพิ่ม library เข้าโปรเจค
5. รัน `MainApp.java` ได้เลย!


## 🎯 วิธีใช้งาน

1. เปิดโปรแกรม
2. คลิก **"Load File"** หรือลากไฟล์ `dept.txt` มาวาง
3. ใส่ค่าระดับน้ำในช่อง **"Liquid depth"**
4. คลิก **"Calculate"** เพื่อคำนวณ
5. ดูผลลัพธ์ในตารางสี:
   - 🔴 **สีแดง**: ไม่มีแก๊ส (0%)
   - 🟡 **สีเหลือง**: แก๊สน้อย (<50%)
   - 🟢 **สีเขียว**: แก๊สมาก (≥50%)

## 👥 ทีมพัฒนา

**C++ TEAM**
- **วชิรวิทย์ วงค์แสง** (67011212055) - Project Manager
- **ชินดนัย ภูหัดสวน** (67011212026) - Lead Developer  
- **นางสาวเศรณี ภูนาโพธิ์** (67011212143) - UI/UX Designer

## 🛠️ เทคโนโลยี

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" height="40" alt="intellij logo"  />
</div>

- **Java Swing** - GUI Framework
- **FlatLaf** - Modern Look and Feel
- **Java 2D Graphics** - การวาดกราฟิก



