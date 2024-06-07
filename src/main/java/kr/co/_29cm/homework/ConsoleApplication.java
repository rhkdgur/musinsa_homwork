package kr.co._29cm.homework;

import kr.co._29cm.homework.modules.order.dto.OrderAppDTO;
import kr.co._29cm.homework.modules.order.dto.OrderAppItemDTO;
import kr.co._29cm.homework.modules.order.service.OrderAppService;
import kr.co._29cm.homework.modules.product.dto.ProductDTO;
import kr.co._29cm.homework.modules.product.dto.ProductDefaultDTO;
import kr.co._29cm.homework.modules.product.service.ProductService;
import kr.co._29cm.homework.modules.util.PayAppDisplayUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : kr.co._29cm.homework
 * fileName       : Application
 * author         : rhkdg
 * date           : 2024-06-07
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-07        rhkdg       최초 생성
 */
@Component
@RequiredArgsConstructor
public class ConsoleApplication {

    private final ProductService productService;

    private final OrderAppService orderAppService;

    public void play() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            while(true) {
                String type = "";
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
                type = bf.readLine();

                //주문, 종료 입력 예외처리
                if(!"o".equals(type) && !"order".equals(type) && !"q".equals(type) && !"quit".equals(type)) {
                    System.out.println("o[order] 또는 q[quit]만 입력해주시기 바랍니다.");
                    continue;
                }

                if("o".equals(type) || "order".equals(type)) {

                    //주문 DTO
                    OrderAppDTO appDTO = new OrderAppDTO();

                    //주문 상품 list
                    List<OrderAppItemDTO> itemList = new ArrayList<>();
                    //주문 상품번호 list
                    List<String> productNumList = new ArrayList<String>();

                    //상품목록 조회 목록 개수 20개
                    ProductDefaultDTO searchDTO = new ProductDefaultDTO();
                    searchDTO.setSize(20);
                    Page<ProductDTO> list = productService.selectProductList(searchDTO);
                    PayAppDisplayUtil.productListDisplay(list.toList());

                    while(true) {
                        String productNum = "";
                        String cnt = "";

                        System.out.print("상품번호 : ");
                        productNum = bf.readLine();

                        //상품번호가 공백이고 주문 상품이 있을 경우
                        if(productNum.isBlank() && itemList.size() > 0) {
                            try {
                                //주문 정보에 주문상품 list setter
                                appDTO.setItemList(itemList);
                                orderAppService.insertOrderApp(appDTO,productNumList);
                            }catch (Exception e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                            //상품번호가 공백일 경우
                        }else if(productNum.isBlank()) {
                            System.out.println("입력된 상품번호가 없습니다. 초기화면으로 이동합니다.");
                            break;
                            //주문 상품가 있는 경우
                        }else {

                            String temp = productNum;
                            ProductDTO productDTO = list.stream().filter(x->x.getProductNum().equals(temp)).findFirst().orElse(null);
                            //상품 존재 여부
                            if(productDTO == null) {
                                System.out.println("입력하신 상품번호는 존재하지 않습니다.");
                                continue;
                            }

                            //중복상품 체크
                            if(itemList.size() > 0) {
                                int len = itemList.stream().filter(x->x.getProductNum().equals(temp)).collect(Collectors.toList()).size();
                                if(len > 0) {
                                    System.out.println("이미 구매상품으로 담긴 상품입니다.");
                                    continue;
                                }
                            }

                            //상품 수량 입력
                            System.out.print("수량 : ");
                            cnt = bf.readLine();
                            if(!isInteger(cnt)) {
                                System.out.println("숫자만 입력해주시기 바랍니다. 다시 상품번호를 입력해주시기 바랍니다.");
                                continue;
                            }

                            //주문 상품 담기
                            OrderAppItemDTO itemDTO = new OrderAppItemDTO();
                            itemDTO.setCnt(Integer.parseInt(cnt));
                            itemDTO.setProductNum(productNum);
                            itemDTO.setName(productDTO.getName());
                            itemDTO.setAmount(productDTO.getPrice()*Integer.parseInt(cnt));
                            itemList.add(itemDTO);
                            productNumList.add(productNum);
                        }
                    }
                }else {
                    System.out.println("고객님의 주문 감사합니다.");
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                bf.close();
            } catch (IOException e) {
                System.err.println("BufferedReader close error");
            }
        }
    }

    /**
     * 숫자 유효성 체크
     * @param cnt
     * @return
     */
    public boolean isInteger(String cnt) {
        try {
            Integer.parseInt(cnt);
        }catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
