package service;

import java.util.List;

import dao.CodeDao;
import dao.CodeDaoImpl;
import dto.CodeDto;

public class CodeServiceImpl implements CodeService {
	public static CodeServiceImpl instance = new CodeServiceImpl();
	private CodeServiceImpl() {}
	public static CodeServiceImpl getInstance() {
		return instance;
	}
	
	CodeDao codeDao = CodeDaoImpl.getInstance();
	
	@Override
	public List<CodeDto> getCodeList() {
		return codeDao.getCodeList();
	}

}
