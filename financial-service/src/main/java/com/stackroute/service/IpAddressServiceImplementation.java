package com.stackroute.service;

import com.stackroute.domain.IPAddress;
import com.stackroute.repositories.IpAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpAddressServiceImplementation implements IpAddressService {

    @Autowired
    private IpAddressRepository ipAddressRepository;

    @Override
    public IPAddress createNewIpAddressNode(IPAddress ipAddress) {
        System.out.println(ipAddress);
        return ipAddress;
    }
}
