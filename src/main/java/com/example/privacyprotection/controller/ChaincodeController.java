package com.example.privacyprotection.controller;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Properties;

@RestController
@RequestMapping("api/chaincode")
public class ChaincodeController {

    @Test
    public void test() throws InvalidArgumentException, CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {

    }
}
