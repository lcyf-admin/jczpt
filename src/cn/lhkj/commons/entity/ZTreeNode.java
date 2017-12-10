package cn.lhkj.commons.entity;

public class ZTreeNode {
	
	private String id;//节点ID
	private String name;//节点名称
	private String pId;//父节点ID
	private String text;//显示文本
	private String iconSkin;//图标css
	private String icon;//
	private String font;//显示的样式
	private String treeCode;
	private String title;
	private boolean open;//是否打开
	private boolean checked;//是否选中
	private boolean drag;//是否可以拖拽
	private boolean isRoot;//是否是根节点
	private boolean isLeaf;//是否是叶子节点

	public ZTreeNode(){
		this.drag = false;
		this.open = true;
		this.isRoot = false;
		this.isLeaf = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
