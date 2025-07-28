import java.awt.*;

public class Settings {

    //data
    public static final int GRID_W = 22;
    public static final int GRID_H = 10;
    public static final double CELL_SIZE = 150.0;
    public static final double DEFAULT_FLUID = 2500.0;
    public static final double TOP_BASE = 200.0;
    public static final double GAS_LIMIT = 0.5;

    //window
    public static final int WIN_W = 1350;
    public static final int WIN_H = 800;
    public static final int CELL_DRAW = 40;
    public static final Boolean WINDOW_MENU = true;

    //Font and FontSize
    public static final Font BIG_FONT = new Font("Tahoma", Font.BOLD, 26);
    public static final Font MID_FONT = new Font("Tahoma", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Tahoma", Font.PLAIN, 13);
    public static final Font TINY_FONT = new Font("Tahoma", Font.PLAIN, 11);

    //icon
    public static final String APP_ICON_PATH = "src/res/icon.png";
    public static final int ICON_SIZE_SMALL = 96;

    //TOP
    public static final String APP_TITLE = "Jewel Suite";
    public static final String APP_VERSION = "v1.0.0";
    public static final String BTN_ABOUT = "About";
    public static final String BTN_CLOSE_ABOUT = "CLOSE";
    public static final String BTN_EXIT = "Exit";
    public static final String EXIT_TITLE = "Exit Confirmation";
    public static final String EXIT_MSG = "Are you sure you want to exit?";

    //makeInputPanel
    public static final String INPUT_LABEL = "Liquid depth (M.)";
    public static final String BTN_CALC = "Calculate";
    public static final String BTN_LOAD = "Load File";
    public static final String BTN_CLEAR = "Clear File";

    //makeLegendPane
    public static final String LEGEND_TITLE = "Description";
    public static final String NO_GAS = "NO GAS (0%)";
    public static final String LOW_GAS = "Low Gas (<50%)";
    public static final String HIGH_GAS = "Lots of Gas (>50%)";

    //makeResultPanel
    public static final String RESULT_TITLE = "Resultant:";
    public static final String TOTAL_GAS = "Total Gas %.2f CB.M";
    public static final String STATUS_READY = "Ready";

    //makeRight
    public static final String GRID_TITLE = "GAS DISTRIBUTION TABLE";

    //STATUS
    public static final String STATUS_LOAD = "Load File : ";
    public static final String STATUS_OK = "Succeed";
    public static final String STATUS_DONE = "File Loading Complete!";
    public static final String STATUS_FAIL = "Failed loading file!";
    public static final String STATUS_ERROR = "Error!";
    public static final String STATUS_CHECK = "Unable to load file. Please check file format.";

    //table
    public static final String GRID_INFO = "Click the file or drag and drop the dept.txt file.";
    public static final String FILE_TITLE = "Place the dept.txt file here";
    public static final String FILE_SUB = "or click to select file";
    public static final String SELECT_FILE_TITLE = "Select file dept.txt";
    public static final String FILE_OF_TYPE = "File Type (*.txt)";

    //doCalc
    public static final String LANG_WARNING = "Please download the file first.";
    public static final String LANG_WARNING2 = "You must load the dept.txt file before calculating.";
    public static final String LANG_WARNING3 = "No file information yet.";
    public static final String LANG_WARNING4 = "Calculation completed";
    public static final String LANG_WARNING5 = "Input error!!!";
    public static final String LANG_WARNING6 = "Please enter a valid number.";
    public static final String LANG_WARNING7 = "Incorrect information";

    //MEMBER1
    public static final String MEMBER1 = "วชิรวิทย์ วงค์แสง";
    public static final String ID1 = "67011212055";
    public static final String JOB1 = "Project Manager";
    public static final String IMG1 = "src/res/team/member_king.jpg";
    //MEMBER2
    public static final String MEMBER2 = "ชินดนัย ภูหัดสวน";
    public static final String ID2 = "67011212026";
    public static final String JOB2 = "Lead Developer";
    public static final String IMG2 = "src/res/team/member2.jpg";
    //MEMBER3
    public static final String MEMBER3= "นางสาวเศรณี ภูนาโพธิ์";
    public static final String ID3 = "67011212143";
    public static final String JOB3 = "UI/UX Designer";
    public static final String IMG3 = "src/res/team/member_mint.jpg";

    //GridUI
    public static final String INFO =
            "Channel: [%d, %d]\n" +
            "Upper Floor: %.1f m3\n" +
            "Lower Floor: %.1f m3\n" +
            "Volume: %.0f m3\n" +
            "Gas: %.1f%%\n" +
            "Status: %s";
    public static final String GAS1 = "No Gas";
    public static final String GAS2 = "Little Gas";
    public static final String GAS3 = "Too Much Gas";

    //showAbout
    public static final String ABOUT_TITLE = "Gas distribution simulation system ";
    public static final String ABOUT_DISTRIBUTION = "By C++ TEAM";
}