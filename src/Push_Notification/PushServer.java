package Push_Notification;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.jdesktop.xswingx.PromptSupport;

public class PushServer {

	private JFrame frmPushserver;

	private Choice appChooce;
	
	private JTextField txtAppId = new JTextField(),
						txtMaster = new JTextField(),
						txtAppKey = new JTextField(),
						txtProvince = new JTextField(),
						txtUserTaggedCustom = new JTextField(),
						txtCIDFilePath = new JTextField(),
						txtp2WebLink = new JTextField(),
						txtp3WindowTitle = new JTextField(),
						txtp3WindowContent = new JTextField(),
						txtp3WindowPic = new JTextField(),
						txtp3WindowLeftBtn = new JTextField(),
						txtp3WindowRightBtn = new JTextField(),
						txtp3AppName = new JTextField(),
						txtp3AppLink = new JTextField();
						
	
	private JTextArea txtpp1Title, txtpp1Content,
						txtp1TransContent, txtp4TransContent;
	
	private JTabbedPane tabPnlPush, tabPnlContent;
	
	private JPanel pnlArgs,
					p1, p2, p3, p4, pp1, pp2,
					pnlUserTagged, pnlUserTaggedCustom, pnlUserCID;
	
	private JCheckBox chckbxRing, chckbxVibrate, chckbxCleanable, chckbxOffline,
						chckbxPlatAndroid = new JCheckBox("Android"),
						chckbxPlatIOS  = new JCheckBox("iOS"),
						chckbxAddTrans, chckbxForceStart;
	
	private JSpinner  spinnerOffline = new JSpinner();;
	
	private JLabel lblOffline = new JLabel(); 
	private JSeparator separator;
	private ButtonGroup UserGroup = new ButtonGroup(),
						CIDSelector = new ButtonGroup();
	
	private JRadioButton rdbtnUserAll, rdbtnUserTagged, rdbtnUserCID,
						rdbtnCIDFile, rdbtnCIDInput;
	
	private JButton btnDone;
	
	private boolean supportAndroid = false, 
					supportiOS = false;
	
	private JComboBox<String> cbxCID = new JComboBox();
	
	private Font fontCN = new Font ("宋体", Font.PLAIN, 12);
	private Font fontEN = new Font("Arial", Font.PLAIN, 12);
	private Color paddingColor = new Color(230, 230, 230);
	
	private String CIDFilePath = "C:\\";
   	private static ArrayList<String> CIDList = new ArrayList<String>();
	
	/**
	 * 自定义类AppName
	 * 记录App的推送参数、支持平台
	 * @author tylzjn
	 */
	public class AppName {
		String name;
		String appId;
		String appKey;
		String master;
		String supportPlatform;
		
		public AppName(String name, String appId, String appkey, String master, String Platform){
			this.name = name;
			this.appId = appId;
			this.appKey = appkey;
			this.master = master;
			this.supportPlatform = Platform;  //应用所支持的平台，两位标识符，0不支持，1支持，第一位Android，第二位iOS
		}
		
		public String getName(){
			return name;
		}
		
		public String getAppId(){
			return appId;
		}
		
		public String getAppKey(){
			return appKey;
		}
		
		public String getMaster(){
			return master;
		}
		
		public String getSupportPlatform(){
			return supportPlatform;
		}
	}
	
	//创建AppName类的实例
	//ToDo，数据库读取
	AppName getuiTest = new AppName("getuiTest", "bm8vNdXFUZ5UAyO4D0VUn7","4Ta71zR9638chSbPBjERJ4","2ljofWMtaVAZjjXQZ7gin6","10");
	AppName a = new AppName("a", "aa","aaa","aaaa","11");
	AppName b = new AppName("b", "bb","bbb","bbbb","01");
	private ArrayList appList = new ArrayList(){{
		add(getuiTest);
		add(a);
		add(b);
	}};


	/**
	 * 启动程序
	 * @author tylzjn
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PushServer window = new PushServer();
					window.frmPushserver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * 构造函数
	 * 创建程序内容
	 * @author tylzjn
	 */
	public PushServer() {
		initialize();
	}

	/**
	 * 初始化窗体内容
	 * 内部调用appDataLoader()方法加载数据
	 * 内部调用panelArgsFiller()，tabbedPanelFiller()三个方法填充对应区域内容
	 * @author tylzjn
	 */
	private void initialize() {
		
		 
		frmPushserver = new JFrame();  //创建界面容器
		frmPushserver.setTitle("PushServer");  //程序名
		frmPushserver.setResizable(false);  //不可调整大小
		frmPushserver.getContentPane().setFont(fontCN);  //标题字体
		frmPushserver.setBounds(100, 100, 450, 560);  //程序大小，相对于屏幕左上角，参数(a,b,c,d)，a,b对应窗体左上角坐标，c,d对应 窗体宽高
		frmPushserver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //点击关闭按钮执行的事件
		frmPushserver.getContentPane().setLayout(null);  //初始化页面布局
		
		//下拉选择框标签
		JLabel lblAppChoose = new JLabel("选择应用");
		lblAppChoose.setFont(fontCN);
		lblAppChoose.setBounds(16, 8, 54, 15);
		frmPushserver.getContentPane().add(lblAppChoose);
		
		//下拉选择框
		appChooce = new Choice();
		appChooce.setBounds(70, 5, 97, 21);
		for (Object i : appList){
			appChooce.add(((AppName) i).getName());
		}
		appChooce.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				appDataLoader();
			}
		});
		frmPushserver.getContentPane().add(appChooce);
		
		appDataLoader();  //填充下拉选择框的内容
		
		//参数Panel
		pnlArgs = new JPanel();
		pnlArgs.setBackground(paddingColor);
		pnlArgs.setBounds(16, 36, 416, 78);
		frmPushserver.getContentPane().add(pnlArgs);
		
		appArgsFiller();  //填充参数Panel的内容
		
		//推送内容和高级设置窗口
		tabPnlContent = new JTabbedPane(JTabbedPane.TOP);
		tabPnlContent.setFont(fontCN);
		tabPnlContent.setBounds(16, 122, 416, 191);
		frmPushserver.getContentPane().add(tabPnlContent);
		
		pp1 = new JPanel();
		pp2 = new JPanel();
		
		tabPnlContent.add(pp1,"推送通知");
		tabPnlContent.add(pp2,"高级设置");
		
		pushArgsFiller(); //填充推送内容和高级设置窗口的内容
		
		//推送方式窗口
		tabPnlPush = new JTabbedPane(JTabbedPane.TOP);
		tabPnlPush.setFont(fontCN);
		tabPnlPush.setBounds(16, 320, 416, 200);
		frmPushserver.getContentPane().add(tabPnlPush);
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();

		tabPnlPush.add(p1,"通知");
		tabPnlPush.add(p2,"网页");
		tabPnlPush.add(p3,"下载");
		tabPnlPush.add(p4,"透传");
		
		pushPnlFiller();
	}

	/**
	 * 获取当前下拉框中的App名称，读取其中数据，刷新界面
	 * @author tylzjn
	 */
	private void appDataLoader() {
		String s = appChooce.getSelectedItem().toString();
		for (Object i : appList) {			
			if (((AppName) i).getName().equals(s)){
				
				//添加参数
				txtAppId.setText(((AppName) i).getAppId());
				txtAppKey.setText(((AppName) i).getAppKey());
				txtMaster.setText(((AppName) i).getMaster());
				
				//读取并设置应用对应的平台
				String flag = ((AppName) i).getSupportPlatform();
				switch (flag.charAt(0)) {
				case '0':
					supportAndroid = false;
					break;
				case '1':
					supportAndroid = true;
					break;
				default:
					break;
				}
				
				switch (flag.charAt(1)) {
				case '0':
					supportiOS = false;
					break;
				case '1':
					supportiOS = true;
					break;
				default:
					break;
				}
				
				chckbxPlatAndroid.setEnabled(supportAndroid);
				chckbxPlatIOS.setEnabled(supportiOS);
				chckbxPlatAndroid.setSelected(supportAndroid);
				chckbxPlatIOS.setSelected(supportiOS);
			}
		}
		
		
	}

	/**
	 * 填充App参数窗口内容
	 * @author tylzjn
	 */
	private void appArgsFiller(){
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {120, 240};
		gbl_panel.columnWeights = new double[]{1, 1};  //两列，列宽比重
		gbl_panel.rowWeights = new double[]{1, 1, 1};  //两行，行高比重
		pnlArgs.setLayout(gbl_panel);
		
		JLabel lblAppId = new JLabel("appId"); //1行1列
		lblAppId.setFont(fontEN);
		lblAppId.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblAppId = new GridBagConstraints();
		gbc_lblAppId.fill = GridBagConstraints.BOTH;
		gbc_lblAppId.gridx = 0;
		gbc_lblAppId.gridy = 0;
		pnlArgs.add(lblAppId, gbc_lblAppId);
		
		JLabel lblAppKey = new JLabel("appKey");  //2行1列
		lblAppKey.setFont(fontEN);
		lblAppKey.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblAppKey = new GridBagConstraints();
		gbc_lblAppKey.fill = GridBagConstraints.BOTH;
		gbc_lblAppKey.gridx = 0;
		gbc_lblAppKey.gridy = 1;
		pnlArgs.add(lblAppKey, gbc_lblAppKey);
		
		JLabel lblMaster = new JLabel("master");  //3行1列
		lblMaster.setFont(fontEN);
		lblMaster.setHorizontalAlignment(SwingConstants.CENTER);
		
		GridBagConstraints gbc_lblMaster = new GridBagConstraints();
		gbc_lblMaster.fill = GridBagConstraints.BOTH;
		gbc_lblMaster.gridx = 0;
		gbc_lblMaster.gridy = 2;
		pnlArgs.add(lblMaster, gbc_lblMaster);
		
		txtAppId.setFont(fontEN);
		txtAppId.setEditable(false);
		
		GridBagConstraints gbc_txtAppId = new GridBagConstraints();
		gbc_txtAppId.fill = GridBagConstraints.BOTH;
		gbc_txtAppId.gridx = 1;
		gbc_txtAppId.gridy = 0;
		pnlArgs.add(txtAppId, gbc_txtAppId);
		
		txtAppKey.setFont(fontEN);
		txtAppKey.setEditable(false);
		
		GridBagConstraints gbc_txtAppKey = new GridBagConstraints();
		gbc_txtAppKey.fill = GridBagConstraints.BOTH;
		gbc_txtAppKey.gridx = 1;
		gbc_txtAppKey.gridy = 1;
		pnlArgs.add(txtAppKey, gbc_txtAppKey);
		
		txtMaster.setFont(fontEN);
		txtMaster.setEditable(false);
		
		GridBagConstraints gbc_txtMaster = new GridBagConstraints();
		gbc_txtMaster.fill = GridBagConstraints.BOTH;
		gbc_txtMaster.gridx = 1;
		gbc_txtMaster.gridy = 2;
		pnlArgs.add(txtMaster, gbc_txtMaster);
		
	}

	/**
	 * 填充推送内容和高级设置窗口内容
	 * @author tylzjn
	 */
	private void pushArgsFiller() {
		
		//推送通知
		pp1.setLayout(null);
		
		JLabel lblpp1Title = new JLabel("通知标题");  //推送通知标题 标签
		lblpp1Title.setFont(fontCN);
		lblpp1Title.setBounds(10, 7, 54, 15);
		pp1.add(lblpp1Title);
		
		txtpp1Title = new JTextArea(new MaxLengthDocument(40));  //推送通知标题
		txtpp1Title.setRows(1);
		txtpp1Title.setLineWrap(true);
		txtpp1Title.setWrapStyleWord(true);
		txtpp1Title.setBounds(0, 0, 380, 10);
		
		JPanel pnlpp1Title = new JPanel();
		pnlpp1Title.setBounds(10, 18, 391, 28);
		pnlpp1Title.add(new JScrollPane(txtpp1Title));
	    pp1.add(pnlpp1Title);
		
		JLabel lblpp1Content = new JLabel("通知内容");  //推送通知内容 标签
		lblpp1Content.setFont(fontCN);
		lblpp1Content.setBounds(10, 45, 54, 15);
		pp1.add(lblpp1Content);
		
		txtpp1Content = new JTextArea(new MaxLengthDocument(600));  //推送通知内容
		txtpp1Content.setRows(6);
		txtpp1Content.setLineWrap(true);
		txtpp1Content.setWrapStyleWord(true);
		txtpp1Content.setBounds(0, 0, 380, 10);
		
		JPanel pnlpp1Content = new JPanel();
		pnlpp1Content.setBounds(10, 56, 391, 108);
		pnlpp1Content.add(new JScrollPane(txtpp1Content));
	    pp1.add(pnlpp1Content);
	    
	   
	    //高级设置
	    pp2.setLayout(null);
	    
	    chckbxRing = new JCheckBox("收到通知响铃");
	    chckbxRing.setFont(fontCN);
	    chckbxRing.setSelected(true);
	    chckbxRing.setBounds(6, 6, 103, 23);
	    pp2.add(chckbxRing);
	    
	    chckbxVibrate = new JCheckBox("收到通知震动");
	    chckbxVibrate.setFont(fontCN);
	    chckbxVibrate.setSelected(true);
	    chckbxVibrate.setBounds(111, 6, 103, 23);
	    pp2.add(chckbxVibrate);
	    
	    chckbxCleanable = new JCheckBox("通知能否清除");
	    chckbxCleanable.setFont(fontCN);
	    chckbxCleanable.setSelected(true);
	    chckbxCleanable.setBounds(216, 6, 169, 23);
	    chckbxCleanable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!chckbxCleanable.isSelected()) {
					chckbxCleanable.setSelected(true);
					UIManager.put("OptionPane.messageFont", fontCN);
					final JDialog JDialogCleanable = new JDialog(frmPushserver, "警告", true);
					JButton btnConfirm = new JButton("继续");
					btnConfirm.setFont(fontCN);
					btnConfirm.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							JDialogCleanable.dispose();
							chckbxCleanable.setSelected(false);
						}
					});
					JButton btnCancel = new JButton("取消");
					btnCancel.setFont(fontCN);
					btnCancel.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							JDialogCleanable.dispose();
						}
					});
					Object[] options ={btnConfirm, btnCancel};
					
					JOptionPane OPCleanable = new JOptionPane("一旦取消，用户将不能清除通知，是否继续？",
							JOptionPane.WARNING_MESSAGE,JOptionPane.YES_NO_OPTION,null, options,options[0]);
					JDialogCleanable.setContentPane(OPCleanable);
					JDialogCleanable.setLocationRelativeTo(frmPushserver);
					JDialogCleanable.setResizable(false);
					JDialogCleanable.pack();
					JDialogCleanable.setVisible(true);
				}
				
			}
		});
	    pp2.add(chckbxCleanable);
	    
	    chckbxOffline = new JCheckBox("是否进离线消息");
	    chckbxOffline.setFont(fontCN);
	    chckbxOffline.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent arg0) {
	    		if(chckbxOffline.isSelected()){
					spinnerOffline.setVisible(true);
					lblOffline.setVisible(true);
				} else {
					spinnerOffline.setVisible(false);
					lblOffline.setVisible(false);
				}
	    	}
	    });
	    chckbxOffline.setSelected(true);
	    chckbxOffline.setBounds(6, 38, 114, 23);
	    pp2.add(chckbxOffline);
	    
	    lblOffline.setText("离线时间(小时)");
	    lblOffline.setFont(fontCN);
	    lblOffline.setBounds(126, 42, 88, 15);
	    pp2.add(lblOffline);
	    
	    spinnerOffline.setFont(fontEN);
	    spinnerOffline.setModel(new SpinnerNumberModel(2, 1, 72, 1));
	    spinnerOffline.setBounds(216, 39, 39, 22);
	    pp2.add(spinnerOffline);
	    
	    separator = new JSeparator();
	    separator.setBounds(8, 65, 395, 15);
	    pp2.add(separator);
	    
	    JLabel lblUser = new JLabel("选择接收用户");
	    lblUser.setFont(fontCN);
	    lblUser.setBounds(18, 73, 72, 15);
	    pp2.add(lblUser);
	    
	    rdbtnUserAll = new JRadioButton("所有用户");
	    rdbtnUserAll.setSelected(true);
	    rdbtnUserAll.setFont(fontCN);
	    rdbtnUserAll.setBounds(110, 70, 90, 20);
	    rdbtnUserAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlUserTagged.setVisible(false);
				pnlUserCID.setVisible(false);
			}
		});
	    pp2.add(rdbtnUserAll);
	    
	    rdbtnUserTagged = new JRadioButton("标签用户");
	    rdbtnUserTagged.setFont(fontCN);
	    rdbtnUserTagged.setBounds(210, 70, 90, 20);
	    rdbtnUserTagged.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlUserTagged.setVisible(true);
				pnlUserCID.setVisible(false);
				
			}
		});
	    pp2.add(rdbtnUserTagged);
	    
	    rdbtnUserCID = new JRadioButton("特定用户");
	    rdbtnUserCID.setFont(fontCN);
	    rdbtnUserCID.setBounds(310, 70, 90, 20);
	    rdbtnUserCID.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnlUserCID.setVisible(true);
				pnlUserTagged.setVisible(false);
				
			}
		});
	    pp2.add(rdbtnUserCID);
	    
	    UserGroup.add(rdbtnUserAll);
	    UserGroup.add(rdbtnUserTagged);
	    UserGroup.add(rdbtnUserCID);
	    
	    pnlUserTagged = new JPanel();
	    pnlUserTagged.setLayout(null);
	    pnlUserTagged.setBounds(18, 92, 375, 69);
	    pnlUserTagged.setBackground(new Color(230, 230, 230));
	    pnlUserTagged.setVisible(false);
	    pp2.add(pnlUserTagged);
	    
	    JLabel lblUserTaggedPlatform = new JLabel("平台");
	    lblUserTaggedPlatform.setFont(fontCN);
	    lblUserTaggedPlatform.setBounds(9, 10, 31, 15);
	    pnlUserTagged.add(lblUserTaggedPlatform);
	    
	    chckbxPlatAndroid.setFont(fontEN);
	    chckbxPlatAndroid.setBounds(35, 8, 71, 18);
	    chckbxPlatAndroid.setBackground(paddingColor);
	    chckbxPlatAndroid.setEnabled(supportAndroid);
	    pnlUserTagged.add(chckbxPlatAndroid);
	    
	    chckbxPlatIOS = new JCheckBox("iOS");
	    chckbxPlatIOS.setFont(fontEN);
	    chckbxPlatIOS.setBounds(105, 8, 71, 18);
	    chckbxPlatIOS.setBackground(new Color(230, 230, 230));
	    chckbxPlatIOS.setEnabled(supportiOS);
	    pnlUserTagged.add(chckbxPlatIOS);
	    
	    pnlUserTaggedCustom = new JPanel();
	    pnlUserTaggedCustom.setLayout(null);
	    pnlUserTaggedCustom.setBounds(166, 6, 199, 30);
	    pnlUserTaggedCustom.setBackground(new Color(230, 230, 230));
//	    pnlUserTaggedCustom.setVisible(false);
	    pnlUserTagged.add(pnlUserTaggedCustom);
	    
	    JLabel lblUserTaggedCustom = new JLabel("自定义标签");
	    lblUserTaggedCustom.setFont(fontCN);
	    lblUserTaggedCustom.setBounds(9, 4, 60, 15);
	    pnlUserTaggedCustom.add(lblUserTaggedCustom);
	    
	    
	    txtUserTaggedCustom = new JTextField(new MaxLengthDocument(20),"",1);
	    PromptSupport.setPrompt("标签1,标签2,标签3", txtUserTaggedCustom);
	    PromptSupport.setForeground(Color.gray, txtUserTaggedCustom);
	    txtUserTaggedCustom.setBounds(75, 0, 120, 20);
	    pnlUserTaggedCustom.add(txtUserTaggedCustom);

	    JLabel lblProvince = new JLabel("省份");
	    lblProvince.setFont(fontCN);
	    lblProvince.setBounds(9, 44, 31, 15);
	    pnlUserTagged.add(lblProvince);
	    
	    JButton btnProvince = new JButton("选择");
	    btnProvince.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProvinceDialog dialog = new ProvinceDialog(frmPushserver);  
			}
		});
	    
	    btnProvince.setFont(fontCN);
	    btnProvince.setBounds(50, 40, 58, 23);
	    pnlUserTagged.add(btnProvince);
	    

	    txtProvince.setEditable(false);
	    txtProvince.setBounds(118, 41, 138, 21);
	    txtProvince.setColumns(10);
	    pnlUserTagged.add(txtProvince);
	    
		pnlUserCID = new JPanel();
		pnlUserCID.setLayout(null);
		pnlUserCID.setBounds(18, 92, 375, 69);
		pnlUserCID.setBackground(new Color(230, 230, 230));
		pnlUserCID.setVisible(false);
	    pp2.add(pnlUserCID);
		
	    rdbtnCIDFile = new JRadioButton("文件读取");
	    rdbtnCIDFile.setFont(fontCN);
	    rdbtnCIDFile.setBackground(paddingColor);
	    rdbtnCIDFile.setBounds(0, 12, 80, 15);
	    pnlUserCID.add(rdbtnCIDFile);
	    
	    txtCIDFilePath.setFont(fontCN);
	    txtCIDFilePath.setBounds(80, 10, 220, 23);
	    PromptSupport.setPrompt("选择用户CID文件，每个ID用换行隔开", txtCIDFilePath);
	    PromptSupport.setForeground(Color.gray, txtCIDFilePath);
	    pnlUserCID.add(txtCIDFilePath);
		
	    JButton btnCIDOpen = new JButton("打开");
	    btnCIDOpen.setFont(fontCN);
	    btnCIDOpen.setBounds(310, 10, 58, 23);
	    btnCIDOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser fcCID = new JFileChooser();
				updateFont(fcCID, fontCN);
				fcCID.setDialogType(JFileChooser.OPEN_DIALOG);

				fcCID.setCurrentDirectory(new File(CIDFilePath));//初始化目录
				fcCID.setFileFilter(new FileNameExtensionFilter("文本", "txt"));
				int result = fcCID.showOpenDialog(null);
				boolean FileCorrect = false;
				if (result == JFileChooser.APPROVE_OPTION) {
					CIDFilePath = fcCID.getSelectedFile().getAbsolutePath();
					try {
						FileCorrect = readCIDFile(CIDFilePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (FileCorrect == true) {
						txtCIDFilePath.setText("CID读取成功--"+CIDFilePath);
					}else {
						txtCIDFilePath.setText("CID读取失败，请重新选择--" + CIDFilePath);
					}
				}
//				for (Object i : CIDList) {
//					System.out.println(i);
//				}
//				
			}
		});
	    pnlUserCID.add(btnCIDOpen);
	    
	    rdbtnCIDInput = new JRadioButton("手动输入");
	    rdbtnCIDInput.setFont(fontCN);
	    rdbtnCIDInput.setBackground(paddingColor);
	    rdbtnCIDInput.setBounds(0, 44, 80, 15);
	    pnlUserCID.add(rdbtnCIDInput);
	    
	    cbxCID.setEditable(true);
	    cbxCID.setFont(fontCN);
	    cbxCID.setBounds(80, 40, 220, 23);
	    ((JTextField) cbxCID.getEditor().getEditorComponent()).setDocument(new MaxLengthDocument(32));
	    cbxCID.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER && cbxCID.getEditor().getItem().toString().length()==32){
					cbxCID.addItem(cbxCID.getEditor().getItem().toString());
					cbxCID.getEditor().setItem("");
					}
			}
		});
	    PromptSupport.setPrompt("输入32位CID，Enter键确认", (JTextComponent) cbxCID.getEditor().getEditorComponent());
	    PromptSupport.setForeground(Color.GRAY, (JTextComponent) cbxCID.getEditor().getEditorComponent());
	    pnlUserCID.add(cbxCID);
	    
	    
	    JButton btnCIDDel = new JButton("删除");
	    btnCIDDel.setFont(fontCN);
	    btnCIDDel.setBounds(310, 40, 58, 23);
	    btnCIDDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < cbxCID.getItemCount(); i++) {
					if (cbxCID.getItemAt(i).toString().equals(cbxCID.getEditor().getItem().toString())) {
						cbxCID.removeItemAt(cbxCID.getSelectedIndex());
					}
				}
				cbxCID.getEditor().setItem("");
			}
		});
	    pnlUserCID.add(btnCIDDel);
	    
	    CIDSelector.add(rdbtnCIDFile); 
	    CIDSelector.add(rdbtnCIDInput);
	    rdbtnCIDFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtCIDFilePath.setEnabled(true);
				btnCIDOpen.setEnabled(true);
				cbxCID.setEnabled(false);
				btnCIDDel.setEnabled(false);
			}
		});
	    rdbtnCIDInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtCIDFilePath.setEnabled(false);
				btnCIDOpen.setEnabled(false);
				cbxCID.setEnabled(true);
				btnCIDDel.setEnabled(true);
			}
		});
	    rdbtnCIDFile.setSelected(true);
	    cbxCID.setEnabled(false);
		btnCIDDel.setEnabled(false);

	}
	
	/**
	 * 填充推送方式窗口内容
	 */
	private void pushPnlFiller() {
		
		//推送通知
		p1.setLayout(null);
		
		chckbxAddTrans = new JCheckBox("附带透传信息");
		chckbxAddTrans.setFont(fontCN);
		chckbxAddTrans.setBounds(6, 6, 103, 20);
		p1.add(chckbxAddTrans);
		
		txtp1TransContent = new JTextArea(new MaxLengthDocument(600));  //推送通知内容
		txtp1TransContent.setRows(6);
		txtp1TransContent.setLineWrap(true);
		txtp1TransContent.setWrapStyleWord(true);
		txtp1TransContent.setBounds(0, 0, 380, 10);
		
		JPanel pnlp1TransContent = new JPanel();
		pnlp1TransContent.setBounds(10, 21, 391, 108);
		pnlp1TransContent.add(new JScrollPane(txtp1TransContent));
	    p1.add(pnlp1TransContent);
	    
		chckbxForceStart = new JCheckBox("点击通知启动应用");
		chckbxForceStart.setFont(fontCN);
		chckbxForceStart.setBounds(6, 125, 133, 23);
		chckbxForceStart.setSelected(true);
		p1.add(chckbxForceStart);
		
	    JButton btnp1Send = new JButton("发送通知");
	    btnp1Send.setFont(fontCN);
	    btnp1Send.setBounds(147, 144, 93, 23);
	    btnp1Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pushtoAPP.Notification();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	    p1.add(btnp1Send);
	    
		p2.setLayout(null);
		
		JLabel lblp2WebLink = new JLabel("网页地址");
		lblp2WebLink.setFont(fontCN);
		lblp2WebLink.setBounds(10, 6, 80, 23);
		p2.add(lblp2WebLink);
		
		txtp2WebLink.setFont(fontCN);
		txtp2WebLink.setBounds(14, 26, 383, 23);
		txtp2WebLink.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (txtp2WebLink.getText().equals("")) {
					txtp2WebLink.setText("http://");
				}
				super.focusGained(arg0);
			}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtp2WebLink.getText().equals("http://")) {
					txtp2WebLink.setText("");
				}
				super.focusLost(arg0);
			}
		});
	    PromptSupport.setPrompt("输入待推送网址", txtp2WebLink);
	    PromptSupport.setForeground(Color.gray, txtp2WebLink);
	    p2.add(txtp2WebLink);
	    
	    JButton btnp2Send = new JButton("发送通知");
	    btnp2Send.setFont(fontCN);
	    btnp2Send.setBounds(147, 144, 93, 23);
	    btnp2Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pushtoAPP.Notification();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	    p2.add(btnp2Send);
	    
		p3.setLayout(null);

		JLabel lblp3WindowTitle = new JLabel("弹窗标题");
		lblp3WindowTitle.setFont(fontCN);
		lblp3WindowTitle.setBounds(10, 6, 80, 23);
		p3.add(lblp3WindowTitle);
		
		txtp3WindowTitle.setDocument(new MaxLengthDocument(20));
		txtp3WindowTitle.setFont(fontCN);
		txtp3WindowTitle.setBounds(14, 26, 130, 23);
	    PromptSupport.setPrompt("输入弹窗标题", txtp3WindowTitle);
	    PromptSupport.setForeground(Color.gray, txtp3WindowTitle);
	    p3.add(txtp3WindowTitle);
	    
	    JLabel lblp3WindowContent = new JLabel("弹窗内容");
	    lblp3WindowContent.setFont(fontCN);
	    lblp3WindowContent.setBounds(150, 6, 80, 23);
		p3.add(lblp3WindowContent);
		
		txtp3WindowContent.setDocument(new MaxLengthDocument(50));
		txtp3WindowContent.setFont(fontCN);
		txtp3WindowContent.setBounds(154, 26, 243, 23);
	    PromptSupport.setPrompt("输入弹窗内容", txtp3WindowContent);
	    PromptSupport.setForeground(Color.gray, txtp3WindowContent);
	    p3.add(txtp3WindowContent);
	    
	    JLabel lblp3WindowLeftBtn = new JLabel("左按钮");
	    lblp3WindowLeftBtn.setFont(fontCN);
	    lblp3WindowLeftBtn.setBounds(10, 50, 80, 23);
		p3.add(lblp3WindowLeftBtn);

		txtp3WindowLeftBtn = new JTextField(new MaxLengthDocument(10), "下载", 1);
		txtp3WindowLeftBtn.setFont(fontCN);
		txtp3WindowLeftBtn.setBounds(14, 70, 60, 23);
	    p3.add(txtp3WindowLeftBtn);
	    
	    JLabel lblp3WindowRightBtn = new JLabel("右按钮");
	    lblp3WindowRightBtn.setFont(fontCN);
	    lblp3WindowRightBtn.setBounds(80, 50, 80, 23);
		p3.add(lblp3WindowRightBtn);
		
		txtp3WindowRightBtn = new JTextField(new MaxLengthDocument(10), "取消", 1);
		txtp3WindowRightBtn.setFont(fontCN);
		txtp3WindowRightBtn.setBounds(84, 70, 60, 23);
	    p3.add(txtp3WindowRightBtn);
	    
	    JLabel lblp3WindowPic = new JLabel("弹窗图片");
	    lblp3WindowPic.setFont(fontCN);
	    lblp3WindowPic.setBounds(150, 50, 80, 23);
		p3.add(lblp3WindowPic);
		
		txtp3WindowPic.setDocument(new MaxLengthDocument(150));
		txtp3WindowPic.setFont(fontCN);
		txtp3WindowPic.setBounds(154, 70, 243, 23);
	    PromptSupport.setPrompt("输入图片网址", txtp3WindowPic);
	    PromptSupport.setForeground(Color.gray, txtp3WindowPic);
	    p3.add(txtp3WindowPic);
	    
	    JLabel lblp3AppName = new JLabel("应用名称");
	    lblp3AppName.setFont(fontCN);
	    lblp3AppName.setBounds(10, 94, 80, 23);
		p3.add(lblp3AppName);
		
		txtp3AppName.setDocument(new MaxLengthDocument(40));
		txtp3AppName.setFont(fontCN);
		txtp3AppName.setBounds(14, 114, 130, 23);
	    PromptSupport.setPrompt("输入应用名称", txtp3AppName);
	    PromptSupport.setForeground(Color.gray, txtp3AppName);
	    p3.add(txtp3AppName);
	    
	    JLabel lblp3AppLink = new JLabel("应用下载地址");
	    lblp3AppLink.setFont(fontCN);
	    lblp3AppLink.setBounds(150, 94, 80, 23);
		p3.add(lblp3AppLink);
		
		txtp3AppLink.setDocument(new MaxLengthDocument(150));
		txtp3AppLink.setFont(fontCN);
		txtp3AppLink.setBounds(154, 114, 243, 23);
	    PromptSupport.setPrompt("输入应用下载地址", txtp3AppLink);
	    PromptSupport.setForeground(Color.gray, txtp3AppLink);
	    p3.add(txtp3AppLink);

	    JButton btnp3Send = new JButton("发送通知");
	    btnp3Send.setFont(fontCN);
	    btnp3Send.setBounds(147, 144, 93, 23);
	    btnp3Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pushtoAPP.Notification();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	    p3.add(btnp3Send);
		
	    p4.setLayout(null);

		JLabel lblp4TransContent = new JLabel("透传内容");
		lblp4TransContent.setFont(fontCN);
		lblp4TransContent.setBounds(10, 6, 80, 23);
		p4.add(lblp4TransContent);
		
		txtp4TransContent = new JTextArea(new MaxLengthDocument(600));  //推送通知内容
		txtp4TransContent.setRows(6);
		txtp4TransContent.setLineWrap(true);
		txtp4TransContent.setWrapStyleWord(true);
		txtp4TransContent.setBounds(0, 0, 380, 10);
		
		JPanel pnlp4TransContent = new JPanel();
		pnlp4TransContent.setBounds(10, 21, 391, 108);
		pnlp4TransContent.add(new JScrollPane(txtp4TransContent));
	    p4.add(pnlp4TransContent);
	    
	    JButton btnp4Send = new JButton("发送通知");
	    btnp4Send.setFont(fontCN);
	    btnp4Send.setBounds(147, 144, 93, 23);
	    btnp4Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try {
//					pushtoAPP.Notification();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		});
	    p4.add(btnp4Send);
	}
	
	/**
	 * 限制文本框的输入内容长度
	 * @author tylzjn
	 */
	class MaxLengthDocument extends PlainDocument{
	    int   maxChars;
	    public MaxLengthDocument(int max){
	        maxChars = max;
	    }
	    public void insertString(int offset, String s, AttributeSet a)throws BadLocationException{
	        if(getLength()+ s.length() > maxChars){
	            Toolkit.getDefaultToolkit().beep();
	            return;
	        } else if (s.length()== 0){
	        	System.out.println(getContent().length());
	        	return;
	        }
	        super.insertString(offset,s,a);
	    }
	    @Override
	    protected AbstractElement createDefaultRoot() {
	    	// TODO Auto-generated method stub
	    	return super.createDefaultRoot();
	    }
	}
	
	/**
	 * 推送包裹，综合信息
	 */
	private Map<String, Object> GeneralInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("AppId", txtAppId.getText());
		map.put("Appkey", txtAppKey.getText());
		map.put("Title", txtpp1Title.getText());
		map.put("Content", txtpp1Content.getText());
		map.put("IsRing", chckbxRing.isSelected());
		map.put("IsVibrate", chckbxVibrate.isSelected());
		map.put("IsCleanable", chckbxCleanable.isSelected());
		map.put("Offline", chckbxOffline.isSelected());
		map.put("OfflineTime", spinnerOffline.getValue());
		
		return map;
	} 
	
	
	//实现省份选择对话框，最多选择三个
	String[] provinceName = new String[]{"北京市","天津市","上海市","重庆市",
			"河北","山西","内蒙古","辽宁","吉林","黑龙江","江苏","浙江","安徽",
			"福建","江西","山东","河南","湖北","湖南","广东","广西","海南","四川",
			"贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆","香港","澳门","台湾"};
	
	ArrayList provinceSelected = new ArrayList();

	/**
	 * 
	 * 弹出的省份选择对话框及其内容实现
	 * 动态添加省份的多选框，及对应监听，
	 * 读取provinceSelected列表添加选中的项
	 * 完成按钮对自身文字进行判断，提交对话框修改省份文本内容
	 * @author tylzjn
	 */
	class ProvinceDialog{
		
		JDialog jDialogProvince = null;
		
		ProvinceDialog(JFrame jFrame){
			jDialogProvince = new JDialog(jFrame,"选择省份",true);
			jDialogProvince.setSize(400, 250);
			jDialogProvince.setResizable(false);
			jDialogProvince.setLocationRelativeTo(frmPushserver);
            Container cc = jDialogProvince.getContentPane();
            cc.setLayout(null);
            
            JPanel pnlDialog = new JPanel();
            pnlDialog.setBounds(10, 0, 380, 180);
            cc.add(pnlDialog);
            pnlDialog.setLayout(new GridLayout(0, 5));
            JCheckBox[] chckbxPlace = new JCheckBox[provinceName.length+1];
            
    		for (int i = 0; i < provinceName.length; i++) {
    			chckbxPlace[i] = new JCheckBox(provinceName[i]);
    			chckbxPlace[i].setFont(fontCN);
    			chckbxPlace[i].addActionListener(new chckbxClick(i));
    			pnlDialog.add(chckbxPlace[i]);
    		}
    		
    		for (Object i : provinceSelected){
    			chckbxPlace[(int) i].setSelected(true);
    		}
    		
    		JSeparator separator = new JSeparator();
    	    separator.setBounds(8, 180, 380, 5);
    	    cc.add(separator);
    	    
    		btnDone = new JButton("完成");
    		btnDone.setFont(fontCN);
    		btnDone.setBounds(155, 190, 90, 23);
    		btnDone.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btnDone = (JButton) e.getSource();
					if (btnDone.getText().equals("完成")) {
						txtProvince.setText("");
						for (Object i : provinceSelected){
							txtProvince.setText(txtProvince.getText()+" " + provinceName[(int) i]);
						}
						jDialogProvince.dispose();
					}
				}
			});
    		cc.add(btnDone);
    		
    		jDialogProvince.setVisible(true);
    		
		}
		
		/**
		 * 省份多选框的监听器，传入省份的位置i
		 * 如果选中则向provinceSelected列表中添加省份的位置
		 * 如果取消选中则在provinceSelected列表中查找到这个省份的位置并将之移除
		 * 最后对选中个数进行判断，如果多于三个，则改变提交按钮标题
		 * @author tylzjn
		 */
		class chckbxClick implements ActionListener{
			
			private int position = 0;
			
			public chckbxClick(int i) {
				this.position = i;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox chckbx = (JCheckBox) e.getSource();
				int c = provinceSelected.size();

				if (chckbx.isSelected() == true) {
					provinceSelected.add(position);
				}else {
			        
			        for(int i=0; i<c; i++){
//			        	System.out.println((int)provinceSelected.get(i));
			            if(position==(int)provinceSelected.get(i)){
			            	provinceSelected.remove(i);
			                i--;
			                c--;
			            }
			        }
				}
//				System.out.println(provinceSelected);
				c = provinceSelected.size();
				if (c>3) {
					btnDone.setText("请选择三个以内的省份");
					btnDone.setBounds(120, 190, 160, 23);
				}else {
					btnDone.setText("完成");
					btnDone.setBounds(155, 190, 90, 23);
				}
			}
			
		}
	}
	
	/**
	 * 更新对象内部所拥有控件的字体，被用于弹出的CID文件选择框
	 * @param comp
	 * @param font
	 */
	private static void updateFont(Component comp, Font font) {  
	    comp.setFont(font);  
	    if (comp instanceof Container) {  
	        Container c = (Container) comp;  
	        int n = c.getComponentCount();  
	        for (int i = 0; i < n; i++) {  
	            updateFont(c.getComponent(i), font);  
	        }  
	    }  
	} 
	
	/**
	 * 读取CID文件，将CID信息存储于CIDList中
	 * @param filePath CID文件路径
	 * @return 返回CID信息是否读取成功的布尔值
	 * @throws IOException
	 */
	public static final boolean readCIDFile(String filePath) throws IOException {      
        BufferedReader br = new BufferedReader(new InputStreamReader(
        new FileInputStream(filePath)));
        boolean FileCorrect = false;
        CIDList = new ArrayList<String>();
        for (String line = br.readLine(); line != null; line = br.readLine()) {
        	FileCorrect = true;
        	if (line.length()==32) {
        		CIDList.add(line);
			}else {
				FileCorrect = false;
			}
        }
        br.close();
		return FileCorrect;
    }

	
	
	
}
