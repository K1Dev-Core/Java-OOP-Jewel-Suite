# ButtonHelper.java - สร้างปุ่มที่มีสไตล์พิเศษ

## ภาพรวม
ButtonHelper เป็นคลาส utility ที่ใช้สร้างปุ่มที่มีการออกแบบพิเศษ รองรับเอฟเฟกต์ hover, pressed และมีเงาตกกระทบ

## เมธอดหลัก

### createButton(String text, Color bgColor, int width, int height)
สร้างปุ่มที่มีสไตล์พิเศษ:
- รับพารามิเตอร์: ข้อความ, สีพื้นหลัง, ความกว้าง, ความสูง
- คืนค่า JButton ที่มีการปรับแต่งแล้ว
- ใช้ CustomButton (inner class) สำหรับการแสดงผล

## CustomButton (Inner Class)

### ตัวแปรสำคัญ
- `boolean isHover` - สถานะเมาส์ชี้อยู่บนปุ่ม
- `boolean isPressed` - สถานะปุ่มถูกกด
- `float shadowAlpha` - ความโปร่งใสของเงา
- `Color bgColor` - สีพื้นหลังของปุ่ม

### Constructor
กำหนดคุณสมบัติพื้นฐานของปุ่ม:
- ขนาดและฟอนต์
- สีข้อความ (ขาวหรือเทาเข้มตามความสว่างของพื้นหลัง)
- ปิดการแสดงผล focus และ border เริ่มต้น
- เปลี่ยน cursor เป็นรูปมือ
- เพิ่ม MouseListener สำหรับจัดการเหตุการณ์

### paintComponent(Graphics g)
เมธอดหลักสำหรับวาดปุ่ม:

#### 1. การเตรียมการ
- สร้าง Graphics2D และเปิด antialiasing
- ปรับขนาดและตำแหน่งเมื่อปุ่มถูกกด

#### 2. การวาดเงา
- วาดเงาสีดำโปร่งใส
- ปรับความเข้มของเงาตามสถานะ hover
- ไม่แสดงเงาเมื่อปุ่มถูกกด

#### 3. การวาดพื้นหลัง
- ใช้ GradientPaint สำหรับเอฟเฟกต์ไล่สี
- ปรับสีตามสถานะ (hover/pressed)
- วาดเป็นรูปสี่เหลี่ยมมุมโค้ง

#### 4. การวาดเส้นขอบ
- ใช้สีที่เข้มกว่าพื้นหลัง
- ความหนา 1.5 พิกเซล

#### 5. เอฟเฟกต์ Highlight
- แสดงแถบสีขาวโปร่งใสด้านบนเมื่อ hover
- สร้างเอฟเฟกต์เงาแสง

### เมธอดช่วย

#### brighten(Color color, float factor)
ทำให้สีสว่างขึ้น:
- เพิ่มค่า RGB ตามสัดส่วนที่กำหนด
- จำกัดค่าไม่เกิน 255

#### darken(Color color, float factor)
ทำให้สีเข้มขึ้น:
- ลดค่า RGB ตามสัดส่วนที่กำหนด
- จำกัดค่าไม่ต่ำกว่า 0

### isLightColor(Color color)
ตรวจสอบว่าสีสว่างหรือเข้ม:
- ใช้สูตร luminance: 0.299*R + 0.587*G + 0.114*B
- คืนค่า true หากความสว่าง > 0.7
- ใช้สำหรับกำหนดสีข้อความ

## การจัดการเหตุการณ์

### Mouse Events
- **mouseEntered**: เปลี่ยน isHover = true และ repaint
- **mouseExited**: เปลี่ยน isHover = false และ repaint  
- **mousePressed**: เปลี่ยน isPressed = true และ repaint
- **mouseReleased**: เปลี่ยน isPressed = false และ repaint

## เอฟเฟกต์พิเศษ

### Hover Effect
- สีพื้นหลังสว่างขึ้น 10%
- เงาจางลง
- แสดงแถบ highlight ด้านบน

### Pressed Effect
- สีพื้นหลังเข้มขึ้น 10%
- ปุ่มเลื่อนลง 1 พิกเซล
- ไม่แสดงเงา

### Shadow Effect
- เงาสีดำโปร่งใส
- ตำแหน่งเลื่อน 2 พิกเซล
- ปรับความเข้มตามสถานะ

## การใช้งาน

### ตัวอย่างการสร้างปุ่ม
```java
JButton saveButton = ButtonHelper.createButton("บันทึก", Colors.SUCCESS_GREEN, 120, 40);
JButton cancelButton = ButtonHelper.createButton("ยกเลิก", Colors.DANGER_RED, 120, 40);
```

## ความสัมพันธ์กับคลาสอื่น
- ใช้สีจาก Colors class
- ถูกใช้โดย MainApp และ Display สำหรับสร้างปุ่มต่างๆ
- รองรับการทำงานร่วมกับ ActionListener

## ข้อดีของการออกแบบ
- ปุ่มมีรูปลักษณ์ที่ทันสมัยและสวยงาม
- เอฟเฟกต์ตอบสนองการโต้ตอบของผู้ใช้
- ปรับสีข้อความอัตโนมัติตามพื้นหลัง
- ใช้งานง่ายด้วยเมธอด static เดียว