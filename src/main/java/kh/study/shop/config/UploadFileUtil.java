package kh.study.shop.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kh.study.shop.item.vo.ImgVO;

public class UploadFileUtil {
	//파일이 첨부될 경로 +\\ 추가하기
	private static final String UPLOAD_PATH = "D:\\workspaceSTS\\Shop\\src\\main\\resources\\static\\image\\";
	
	//파일 첨부
	public static ImgVO uploadFile(MultipartFile mainImg) {
		//여기서 선언하여 return 시 사용가능하도록 만듦
		String fileName = null;		//첨부 파일명
		String originFileName = null;	//원본 파일명
		
		//실제 첨부파일이 있을 때만 첨부파일 기능 실행. 첨부파일 없다면 빈문자로 들어옴
		if(!mainImg.isEmpty()) {
			//첨부파일 기능 (첨부하려는 원본 파일명)
			originFileName =	mainImg.getOriginalFilename();
			
			//랜덤 문자 만들기(파일명 중복방지)
			String uuid = UUID.randomUUID().toString();
			
			//원본파일명에서 확장자(.jpg)만 가져오기
			String extension = originFileName.substring(originFileName.lastIndexOf(".")); //"apple.jpg".substring(3) -> "le.jpg"
			//indexOf("매개변수") 매개변수에 들어온 문자가 몇번째에 위치하는지 숫자로 알려줌
			//lastIndexOf("매개변수") 중복된 문자 중 가장 마지막꺼 위치 알려줌
			
			//첨부될 파일명 생성
			fileName = uuid + extension;
			
			try {
				//파일 객체 생성 (파일경로 + 파일명)
				File file = new File(UPLOAD_PATH + fileName);
				
				//파일 업로드
				mainImg.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	
		//원본파일명 첨부파일명 둘다 가져가야 함 ImgVO
		//리턴 해야하는 데이터 저장 위한 객체
		ImgVO imgVO = new ImgVO();
		
		imgVO.setAttachedName(fileName);	//첨부파일명
		imgVO.setOriginName(originFileName);	//원본파일명
		imgVO.setIsMain("Y");
		
		return imgVO;
	}
	
	//다중 파일 첨부
	public static List<ImgVO> multiUploadFile(List<MultipartFile> subImgs) {
		
		List<ImgVO> list = new ArrayList<>();
		
		//첨부된 파일의 개수만큼 첨부 할 것
		for(MultipartFile subImg :subImgs) {
			ImgVO vo =	uploadFile(subImg);		//uploadFile() 자체가 위에서 만든 메소드 불러온것
			//메소드의 리턴값이 imgVO값으로 되기 때문에 list에 담는다.
			vo.setIsMain("N");
			list.add(vo);
		}
		
		
		return list;
	}
}
