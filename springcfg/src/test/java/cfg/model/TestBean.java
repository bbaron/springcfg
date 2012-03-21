package cfg.model;

public class TestBean {

	private String prop1;
	private String prop2;

	public TestBean() {
	}

	public TestBean(String prop) {
		this(prop, prop);
	}

	public TestBean(String prop1, String prop2) {
		this.prop1 = prop1;
		this.prop2 = prop2;
	}

	public String getProp1() {
		return prop1;
	}

	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	public String getProp2() {
		return prop2;
	}

	public void setProp2(String prop2) {
		this.prop2 = prop2;
	}

	@Override
	public String toString() {
		return "TestBean [prop1=" + prop1 + ", prop2=" + prop2 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prop1 == null) ? 0 : prop1.hashCode());
		result = prime * result + ((prop2 == null) ? 0 : prop2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TestBean other = (TestBean) obj;
		if (prop1 == null) {
			if (other.prop1 != null) {
				return false;
			}
		} else if (!prop1.equals(other.prop1)) {
			return false;
		}
		if (prop2 == null) {
			if (other.prop2 != null) {
				return false;
			}
		} else if (!prop2.equals(other.prop2)) {
			return false;
		}
		return true;
	}

}
