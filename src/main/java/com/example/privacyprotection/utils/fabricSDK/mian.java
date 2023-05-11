//package com.example.privacyprotection.utils.fabricSDK;
//
//
//import com.tjut.dcs.digitalcollectionsys.entity.Caretaker;
//import org.junit.Test;
//
//import static com.tjut.dcs.digitalcollectionsys.config.PathConf.*;
//
///**
// * @author WPPStart
// * @PROJECT_NAME:sdkdemo
// * @create
// * @date:2022-05-23-15:36
// * @DESCRIPTION:
// */
//public class mian {
//
//    public static void main(String[] args) {
////        FabricSDK fabricSDK = new FabricSDK("adminInfo");
////        fabricSDK.installChaincode("1",chaincodeLocation,"adminInfo");
////        fabricSDK.instantiated("adminInfo","1");
//        Caretaker caretaker = new Caretaker();
//        caretaker.setCareId("20195261");
//        caretaker.setCareAddress("tjut");
//        caretaker.setCareLive("29aho");
//        caretaker.setCareName("wpwpwp");
//        caretaker.setCarePhone("131546132");
//        caretaker.setCarePassword("zsezes");
//        caretaker.setCareUsername("admin");
//        caretaker.setCareSex("man");
//        System.out.println("caretaker = " + caretaker);
//        String[] initArgsQuery = {"20195261"};
//        String[] initArgsInvoke =
//                {caretaker.getCareId(),
//                        "{\"careId\":\"" + caretaker.getCareId() + "\"," +
//                                "\"carePassword\":\"" + caretaker.getCareName() + "\"," +
//                                "\"careName\":\"" + caretaker.getCareName() + "\"," +
//                                "\"userUsername\":\"" + caretaker.getCareUsername() + "\"," +
//                                "\"careSex\":\"" + caretaker.getCareSex() + "\"," +
//                                "\"careAddress\":\"" + caretaker.getCareAddress() + "\"," +
//                                "\"carePhone\":\"" + caretaker.getCarePhone() + "\"," +
//                                "\"careLive\":\"" + caretaker.getCareLive() + "\"}"
//                };
////        fabricSDK.invoke(initArgsInvoke);
////        Collection collection = fabricSDK.queryChaincode(initArgsQuery);
////        collection.forEach(System.out::println);
//    }
//    @Test
//    public void artworkChaincode(){
//        FabricSDK fabricSDK = new FabricSDK("ArtWorkInfo","artworkchannel");
////        fabricSDK.installChaincode("1",chaincodeLocation,"ArtWorkInfo");
//        fabricSDK.instantiated("ArtWorkInfo","1");
//    }
//
//
//
//
//}
