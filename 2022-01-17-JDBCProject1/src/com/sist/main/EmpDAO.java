package com.sist.main;

// ����Ŭ ���� => �ۼ��� ==> VO,DAO ,Manager (����:�Ϲ� main, JSP, Spring)
import java.util.*; // List
import java.sql.*; // Connection(����) , PreparedStatement(�ۼ���)
// ResultSet (������� �������ִ� ����)
/*
 *   DDL / DML�� ����� �� �ִ� 
 *   DDL 
 *    CREATE TABLE , DROP , RENAME , ALTER .... (����Ŭ������ ���� ó��)
 *    DML (SELECT , INSERT , UPDATE , DELETE)
 *    ---------- ��,�����ͺ��̽� ���α׷��� => ������� SQL�� ��� ���� ó��
 *    ---------- ����ڿ� ��û (�����ͺ��̽��� �ʿ��� => �����͸� �Է�)
 */

public class EmpDAO {
	// ���� ��ü ==> Socket
	private Connection conn; // interface => ��� �����ͺ��̽� ó���� ����
	// ����Ŭ , MS-SQL , MySql .... (����̹����� �����ϸ�)
	// 1. ����̹��� , 2. URL , 3. username , 4. password
	// ���⼭ ����ϴ� SQL������ ǥ��ȭ�� �Ǿ� �ִ�
	// SQL���� ���� ==> BufferedReader / OutputStream
	private PreparedStatement ps; // �̸� SQL������ ���� => ���߿� ���� ä���� ����
	// ����Ŭ �ּ�(1521) : MS-SQL(1433) , MYSQL(3603)
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	// HTML/CSS => JSP (MVC) => Spring / AWS
	// MyBatis / React / Vue / Spring-Boot / Chatbot
	// MariaDB
	// ����̹� ���� => 1�� ���� (������)
	// => classȭ => �������� Ȱ�� => .jar
	public EmpDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// OracleDriver => �о���� Ŭ���� (DriverManager)
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ����Ŭ ���� : 285page ~ 293page => �����α׷����� �ٽ�
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception ex) {
		}
	}

	// ����Ŭ ����
	public void disConnection() {
		try {
			// ���� ����ϴ� Ŭ���� �ݱ� (�ۼ��� => ps)
			if (ps != null)
				ps.close();
			// ���� ��� ��� Ŭ���� �ݱ� (�ڵ��� ���� => conn)
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
		}
	}

	// �ݺ��� �ִ� ��쿡�� => �޼ҵ�ȭ , ����Ŭ => �ݺ������� ���� => �Լ�ȭ
	// PS/SQL 270page ~ 284page
	// �Լ�(Function) , ���ν��� (���)
	// �ڵ�ȭ ó�� (Ʈ����)
	// -----> �ڹٿ� ���� (CallableStatement)
	/*
	 * �⺻ ����Ʈ : �α��� , ȸ������(���̵� �ߺ�,���̵� ã�� , ������ȣ �˻�) ��������
	 */
	// ���
	// 1. ��� ��� ��� (14�� => 1�� <=> EmpVO) => EmpVO�� 14���� �����ش�
	// List
	public List<EmpVO> empListData() // �ο��� ���� ��� => ������ ������
	{
		// ������
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// ���������� ���� => ó��
			/*
			 * conn hr/happy ����Ǿ����ϴ�.
			 */
			// 1. ����
			getConnection();
			// 2. SQL������ �ۼ�
			String sql = "SELECT * FROM emp";
			// 3. SQL������ ����Ŭ�� ����
			ps = conn.prepareStatement(sql);
			// 4. �����Ŀ� ������� ������ �´�
			ResultSet rs = ps.executeQuery();
			while (rs.next()) // ������ ����� ù��°�ٿ� Ŀ���� ��ġ
			// ������ �������鼭 ���پ� �о� �´�
			// next()�� ���پ� �о� �´�
			{
				EmpVO vo = new EmpVO();// �Ѹ��� ���� ���� ����ش�
				// 8���� �����͸� ���ÿ� ����
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));

				list.add(vo); // 14���� ���� ������ ��Ƽ� ó��
				/*
				 * ��ü ��� ===========> List �Ѹ� ,�Ѱ� ���� ------> �ش� ~VO
				 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// ���� Ȯ��
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	// 2. �μ� ��� ���
	public List<DeptVO> deptListData() {
		List<DeptVO> list = new ArrayList<DeptVO>();
		/*
		 * List (������ �ִ� (�ε���) , �ߺ��� ���) => interface | ---------------------------- | |
		 * | ArrayList Vector Queue => List�� �����ϰ� �ִ� Ŭ���� | LinkedList => ������ �Ҷ� =>
		 * Object => ���ϴ� ������������ ���� : ���׸���
		 */
		try {
			// 1. ����
			getConnection();
			// 2. SQL����
			String sql = "SELECT * FROM dept";
			// * =>���̺� ����ϴ� ������ ���� �д´�
			// ������ => ����
			// 3. ����Ŭ�� SQL���� ����
			ps = conn.prepareStatement(sql);
			// 4. ������ ������� �޸𸮿� ������ �д�
			ResultSet rs = ps.executeQuery();
			// 5. Ŭ���̾�Ʈ�� �����ϱ� ���� List�� �����͸� ���� �д�
			while (rs.next()) {
				DeptVO vo = new DeptVO();
				vo.setDeptno(rs.getInt(1)); // ����Ŭ �����ʹ� 1������
				vo.setDname(rs.getString(2));
				vo.setLoc(rs.getString(3));
				// vo => ROW�� �ִ� �����͸� ����

				list.add(vo); // list => ���̺��� ��� ������ ������ �ִ�
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}

	// 3. ��� ��� ��� => salgrade
	public List<SalGradeVO> salgradeListData() {
		List<SalGradeVO> list = new ArrayList<SalGradeVO>();
		try {
			// 1. ����
			getConnection();
			// 2. SQL����
			String sql = "SELECT * FROM salgrade";
			// 3. ����Ŭ�� ����
			ps = conn.prepareStatement(sql);
			// 4. ���� ����� �޸𸮿� ���� => ResultSet
			// *** ? (����� ������ �����Ͱ� �ִ� ��쿡�� => ?�� ���� ä���Ŀ� ����)
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SalGradeVO vo = new SalGradeVO();
				vo.setGrade(rs.getInt("grade")); // �÷��� / �ε���
				vo.setLosal(rs.getInt("losal"));
				vo.setHosal(rs.getInt("hisal"));
				// �Լ��� ��Ī�� ���ÿ��� ==> �ε����� �̿��ϴ� ��찡 �� ����

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� Ȯ��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	// 4. ���,�μ� => JOIN => �ڹٿ����� ����Ŭ����
	public List<EmpVO> empDeptJoinData() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����
			getConnection();
			// 2. SQL����
			String sql = "SELECT empno,ename,job,hiredate,sal," + "dname,loc " + "FROM emp,dept "
					+ "WHERE emp.deptno=dept.deptno";
			// 3. SQL���� ����
			ps = conn.prepareStatement(sql);
			// 4. ������ ������� �޸��� ����
			ResultSet rs = ps.executeQuery();
			// 5. �޸𸮿� ����� �����͸� List�� �߰�
			while (rs.next()) {
				EmpVO vo = new EmpVO(); // Row�Ѱ��� ä���
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.getDvo().setDname(rs.getString(6));
				vo.getDvo().setLoc(rs.getString(7));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� ó��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	// 5. ���,��� => JOIN (NON-EQUI-JOIN) =�� ������� �ʴ´�
	// BETWEEN ~ AND
	// INNER JOIN
	// => ANSI JOIN
	public List<EmpVO> empSalgradeJoinData() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����Ŭ ����
			getConnection();
			// 2. SQL���� ����
			String sql = "SELECT empno,ename,job,hiredate,sal,grade " + "FROM emp JOIN salgrade "
					+ "ON sal BETWEEN losal AND hisal";
			// 3. SQL���� ����
			ps = conn.prepareStatement(sql);
			// 4. �����Ŀ� �޸𸮿� ����� ����
			ResultSet rs = ps.executeQuery();
			// 5. �޸𸮿� ����� ������ Ŭ���̾�Ʈ�� �� �� �ְ� List�� ����
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.getSvo().setGrade(rs.getInt(6));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� ó��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	/*
	 * INNER JOIN : ���� ���� ���Ǵ� ���� EQUI_JOIN(������ =)
	 * 
	 * SELECT ~ FROM A,B WHERE A.col=B.col
	 * 
	 * SELECT ~ FROM A JOIN B ON A.col=B.col
	 * 
	 * NON_EQUI_JOIN(������ =�� �ƴ� �ٸ� ������:BETWEEN,IN,��,����)
	 * 
	 * SELECT ~ FROM A,B WHERE sal BETWEEN �� AND ��
	 * 
	 * SELECT ~ FROM A JOIN B ON sal BETWEEN �� AND ��
	 * 
	 * OUTER JOIN => NULL�� �ִ� ��쿡 ������ �б� LEFT OUTER JOIN SELECT ~ FROM A,B WHERE
	 * A.col=B.col(+)
	 * 
	 * SELECT ~ FROM A LEFT OUTER JOIN B ON A.col=B.col
	 * 
	 * 
	 * RIGTH OUTER JOIN SELECT ~ FROM A,B WHERE A.col(+)=B.col
	 * 
	 * SELECT ~ FROM A RIGTH OUTER JOIN B ON A.col=B.col
	 */
	// 6. ���,�μ�,��� => JOIN
	public List<EmpVO> empDeptSalgradeJoinData() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����Ŭ ����
			getConnection();
			// 2. SQL����
			/*
			 * String sql="SELECT empno,ename,job,hiredate,sal,"//emp +"dname,loc," // dept
			 * +"grade " // salgrade +"FROM emp,dept,salgrade "
			 * +"WHERE emp.deptno=dept.deptno " +"AND sal BETWEEN losal AND hisal";
			 */
			String sql = "SELECT empno,ename,job,hiredate,sal," + "dname,loc," + "grade " + "FROM emp JOIN dept "
					+ "ON emp.deptno=dept.deptno " + "JOIN salgrade " + "ON sal BETWEEN losal AND hisal";
			// 3. SQL���� => ����Ŭ�� ����
			ps = conn.prepareStatement(sql);
			// 4. �����Ŀ� �޸𸮿� ���� ��û
			ResultSet rs = ps.executeQuery();
			// 5. �޸𸮿� �ִ� �����͸� List�� �̵�
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				///////////////////////////////////////
				vo.getDvo().setDname(rs.getString(6));
				vo.getDvo().setLoc(rs.getString(7));
				/////////////////// DeptVO
				vo.getSvo().setGrade(rs.getInt(8));
				/////////////////// SalGradeVO

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� ó��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();

		}
		return list;
	}

	// ����� ��û���� �ִ� ��� => ?
	// 7788�� ����� ������ �ִ� ����� ���,�̸�,����,�Ի���,�ٹ���,�μ���,�޿����
	// ��� 1���� �����͸� ��� => EmpVO
	/*
	 * ȭ�� ��� ------- 1. ��� ==> List 2. ��Ͽ��� �Ѱ��� Ŭ�� => �󼼺��� ==> ~VO 3. �Ϲ� ���� =>
	 * boolean(�α���ó��, ���̵� �ߺ�) , int (��������) 4. insert , update ,delete => ����Ŭ ��ü ó��
	 * => void 5. ���� , �帣�� , ������ , �⿬�� .... List<String>
	 * 
	 * ------------------------------------------------------ MovieVO => ��ȭ 1���� ����
	 * ������ ������ �ִ� => List<MovieVO> : ��ȭ �������� ���´� => ����� (��¥ => �ð� => �¼�) EmpVO => ���
	 * 1���� ���� ���� => �μ� , ��� MusicVO => ���� 1���� ���� ���� FoodVO => ���� 1���� ���� ���� RecipeVO
	 * => ������ 1���� ���� ���� ------------ �ش� ���̺��� �÷����� ��ġ + �߰��� ���� ---------
	 * 
	 * �ڹ� <=======> ����Ŭ 1. ����Ŭ�� �ִ� �����͸� �ڹٿ��� �޴´� ����Ŭ (�÷�) = �ڹ�(�������) 2. �������� ������ �ö���
	 * List�� ��� ó��
	 */
	public EmpVO empDetailData(int empno) {
		EmpVO vo = new EmpVO();
		try {
			// 1. ����Ŭ ����
			getConnection();
			// 2. SQL���� ���� (���,�̸�,����,�Ի���,�ٹ���,�μ���,�޿����)
			String sql = "SELECT empno,ename,job,hiredate," + "loc,dname," + "grade,sal " + "FROM emp,dept,salgrade "
					+ "WHERE emp.deptno=dept.deptno " + "AND sal BETWEEN losal AND hisal " + "AND empno=?";
			// 3. SQL������ ����Ŭ�� ����
			ps = conn.prepareStatement(sql);

			// 4. ?�� ���� ä���ش�
			ps.setInt(1, empno); // ?�� ��ȣ�� 1������ '7788'
			// 1��°�� �ִ� ?������ ä���
			// *** ps.setString(2,"ȫ�浿") ==> 'ȫ�浿'�� ��ȯ�Ѵ�
			// WHERE ename=ȫ�浿 (X)
			// WHERE ename='ȫ�浿' (O) => ��¥,���ڿ��� �ݵ�� ''
			// 5. ?�� ���� ä������ => �����û => �޸𸮿� ����
			ResultSet rs = ps.executeQuery();
			rs.next();// ������ ��� ��ġ�� Ŀ�� �̵�
			// 6. EmpVO�� ���� ä���
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setHiredate(rs.getDate(4));
			////////////////// DeptVO => getDvo()
			vo.getDvo().setLoc(rs.getString(5));
			vo.getDvo().setDname(rs.getString(6));
			////////////////// SalGradeVO => getSvo()
			vo.getSvo().setGrade(rs.getInt(7));
			vo.setSal(rs.getInt(8));

			rs.close();

		} catch (Exception ex) {
			// ���� �߻�
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return vo;
	}

	// 7. SubQuery
	/*
	 * ��Ʈ��ũ ��� ���� : ����Ŭ => ���� Ŭ���̾�Ʈ : �ڹ� => ��û (JDBC) ------- �ӵ��� ���� : ����Ŭ���� (DBCP)
	 * ------- �ڵ��� �ʹ� ������� => ORM (MyBatis,Hibernate) => Spring+Mybatis �������� :
	 * SQL+SQL => SQL�� �Ѱ��� ����� ��� ��Ʈ��ũ => �ѹ��� ��Ƽ� ���� ------ SELECT�� : �÷����� =>
	 * ��Į�󼭺����� (���̺� ���������� ������ ����) ����) SELECT (SELECT~ ), (SELECT~ ) FROM table_name
	 * 
	 * FROM�� : ���̺� ��� ��� => �ζ��� �� => Top-N(rownum) , ������ ������ ����) SELECT ~ FROM
	 * (SELECT~) --------- ������ �÷��� ����� ����
	 * 
	 * SELECT ~ FROM (SELECT ~ FROM (SELECT ~) WHERE�� : ���ǰ� ��� ��� ����) ������ �������� :
	 * ���������� ������� 1���� ��� SELECT ~ FROM table_name WHERE �÷��� ������ (SELECT~~)
	 * ---------------- ---------- �������� �������� ������ �������� : �������� ������� �������� ��� SELECT ~
	 * FROM table_name WHERE �÷��� IN,ANY,ALL,SOME (SELECT~~) ----------------
	 * ---------- �������� ��������
	 * 
	 * IN(10,20,30) => ���� ���� > ANY(10,20,30) => 10 >10 < ANY(10,20,30) => 30 <30 =
	 * ANY(10,20,30) => IN(10,20,30) > ALL(10,20,30) => 30 >30 < ALL(10,20,30) => 10
	 * <10 --------------------------- MIN/MAX
	 * 
	 * WHERE deptno > (�ּҰ�) => 10,20,30) deptno > ANY(SELECT DISTINCT deptno FROM
	 * emp) 10 deptno > (SELECT MIN(deptno) FROM emp) 10
	 * 
	 * deptno < ANY(SELECT DISTINCT deptno FROM emp) 30 deptno < (SELECT MAX(deptno)
	 * FROM emp) 30
	 * 
	 */
	// �޿��� ��պ��� ���� �޴� ����� �̸�,����,�Ի���,�޿�,�μ���,�ٹ���
	// �������� => ����
	// �Ѹ�=> EmpVO , ������ => List<EmpVO>
	public List<EmpVO> empSubQueryData() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����Ŭ ����
			getConnection();
			// 2. SQL����
			String sql = "SELECT ROUND(AVG(sal)) FROM emp";
			// 3. ����� �ޱ�
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int avg = rs.getInt(1);
			rs.close();
			ps.close();
			/// �޿� ��հ��� ���Ѵ�
			// 4. SQL����
			sql = "SELECT ename,job,hiredate,sal,dname,loc " + "FROM emp,dept " + "WHERE emp.deptno=dept.deptno "
					+ "AND sal>? " + "ORDER BY ename ASC";
			ps = conn.prepareStatement(sql);
			// ?�� ���� ä���
			ps.setInt(1, avg);
			// 5. ����� �ޱ�
			rs = ps.executeQuery();
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEname(rs.getString(1));
				vo.setJob(rs.getString(2));
				vo.setHiredate(rs.getDate(3));
				vo.setSal(rs.getInt(4));
				vo.getDvo().setDname(rs.getString(5));
				vo.getDvo().setLoc(rs.getString(6));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� ó��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	public List<EmpVO> empSubQueryData2() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����Ŭ ����
			getConnection();
			// 2. SQL����
			/*
			 * String sql="SELECT ROUND(AVG(sal)) FROM emp"; // 3. ����� �ޱ�
			 * ps=conn.prepareStatement(sql); ResultSet rs=ps.executeQuery(); rs.next(); int
			 * avg=rs.getInt(1); rs.close(); ps.close();
			 */
			/// �޿� ��հ��� ���Ѵ�
			// 4. SQL����
			String sql = "SELECT ename,job,hiredate,sal,dname,loc " + "FROM emp,dept " + "WHERE emp.deptno=dept.deptno "
					+ "AND sal>(SELECT ROUND(AVG(sal)) FROM emp) " + "ORDER BY ename ASC";
			ps = conn.prepareStatement(sql);
			// ?�� ���� ä���

			// 5. ����� �ޱ�
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEname(rs.getString(1));
				vo.setJob(rs.getString(2));
				vo.setHiredate(rs.getDate(3));
				vo.setSal(rs.getInt(4));
				vo.getDvo().setDname(rs.getString(5));
				vo.getDvo().setLoc(rs.getString(6));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			// ���� ó��
			ex.printStackTrace();
		} finally {
			// �ݱ�
			disConnection();
		}
		return list;
	}

	// ��Į�� ��������
	/*
	 * SELECT ename,job,hiredate,sal,(SELECT dname FROM dept WHERE
	 * deptno=emp.deptno) dname, (SELECT loc FROM dept WHERE deptno=emp.deptno) loc
	 * FROM emp;
	 */
	// -> JOIN���� ���������� => ������ ���� (�̸�,����,�Ի���, �޿�, �μ���,�ٹ���)
	public List<EmpVO> empSubQueryListData() {
		// 1. ������
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// 1. ����Ŭ ����
			getConnection(); // LIKE '%A%' ==> LIKE '%'||'A'||'%'
			// �ڹٿ��� => ����Ŭ�� ���� (���ڿ�) => ����
			// String sql="SELECT ename,job,(SELECT~)"
			// 2. SQL����
			// SQLDevloper =>
			String sql = "SELECT ename,job,hiredate,sal," + "(SELECT dname FROM dept WHERE deptno=emp.deptno),"
					+ "(SELECT loc FROM dept WHERE deptno=emp.deptno) " + "FROM emp";
			// 3. ����Ŭ�� SQL���� ����
			ps = conn.prepareStatement(sql);
			// 4. ������� �޴´�
			ResultSet rs = ps.executeQuery();
			// 5. ������� List�� ��´�
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEname(rs.getString(1));
				vo.setJob(rs.getString(2));
				vo.setHiredate(rs.getDate(3));
				vo.setSal(rs.getInt(4));
				vo.getDvo().setDname(rs.getString(5));
				vo.getDvo().setLoc(rs.getString(6));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();// �ݱ�
		}
		return list;
	}

	// �ζ��κ�
	/*
	 * SELECT : ��� 1) ��ü ��� (��� ���) 2) ���� => 1�� (�󼼺���) 3) ���� 4) �������� : 1) ��Į�� , 2)
	 * �ζ��κ�
	 */
	// Top-N (������ �~~ �α�˻��� , �α� �������� ~ �α� �Խù� )
	// 1. �޿��� ���� ������ ���� 5���� ���� => �̸�,�Ի���, ����, �޿�
	/*
	 * SELECT ----> 3) FROM ----- 1) WHERE ----- 2) ORDER BY --> 4)
	 * 
	 * ==> �߰��� �����͸� ���� �� �� ���� rownum BETWEEN 6 AND 10 ==> (X)
	 */
	public List<EmpVO> empTon5Data() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		try {
			// ����
			getConnection();
			// SQL����
			String sql = "SELECT ename,job,hiredate,sal,rownum " + "FROM (SELECT ename,job,hiredate,sal "
					+ "FROM emp ORDER BY sal DESC) " + "WHERE rownum<=5";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEname(rs.getString(1));
				vo.setJob(rs.getString(2));
				vo.setHiredate(rs.getDate(3));
				vo.setSal(rs.getInt(4));

				list.add(vo);
			}
			rs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}

}