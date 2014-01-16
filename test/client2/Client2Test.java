/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class Client2Test {
    
    public Client2Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of connect method, of class Client2.
     */
    @Test
    public void testConnect() throws Exception {
        System.out.println("connect");
        String IP = "";
        Client2 instance = null;
        instance.connect(IP);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Client2.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        String username = "";
        String password = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.login(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFile method, of class Client2.
     */
    @Test
    public void testGetFile() throws Exception {
        System.out.println("getFile");
        String filename = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.getFile(filename);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putFile method, of class Client2.
     */
    @Test
    public void testPutFile() throws Exception {
        System.out.println("putFile");
        String path = "";
        String filename = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.putFile(path, filename);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of appendFile method, of class Client2.
     */
    @Test
    public void testAppendFile() throws Exception {
        System.out.println("appendFile");
        String filename = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.appendFile(filename);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteFile method, of class Client2.
     */
    @Test
    public void testDeleteFile() throws Exception {
        System.out.println("deleteFile");
        String filename = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.deleteFile(filename);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeDirectory method, of class Client2.
     */
    @Test
    public void testMakeDirectory() throws Exception {
        System.out.println("makeDirectory");
        String name = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.makeDirectory(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeDirectory method, of class Client2.
     */
    @Test
    public void testRemoveDirectory() throws Exception {
        System.out.println("removeDirectory");
        String name = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.removeDirectory(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeDirectory method, of class Client2.
     */
    @Test
    public void testChangeDirectory() throws Exception {
        System.out.println("changeDirectory");
        String name = "";
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.changeDirectory(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of currentDirectory method, of class Client2.
     */
    @Test
    public void testCurrentDirectory() throws Exception {
        System.out.println("currentDirectory");
        Client2 instance = null;
        String expResult = "";
        String result = instance.currentDirectory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of list method, of class Client2.
     */
    @Test
    public void testList() throws Exception {
        System.out.println("list");
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.list();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listToString method, of class Client2.
     */
    @Test
    public void testListToString() throws Exception {
        System.out.println("listToString");
        Client2 instance = null;
        String expResult = "";
        String result = instance.listToString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of quit method, of class Client2.
     */
    @Test
    public void testQuit() throws Exception {
        System.out.println("quit");
        Client2 instance = null;
        instance.quit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of noop method, of class Client2.
     */
    @Test
    public void testNoop() throws Exception {
        System.out.println("noop");
        Client2 instance = null;
        instance.noop();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLogged method, of class Client2.
     */
    @Test
    public void testIsLogged() {
        System.out.println("isLogged");
        Client2 instance = null;
        boolean expResult = false;
        boolean result = instance.isLogged();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResponse method, of class Client2.
     */
    @Test
    public void testGetResponse() {
        System.out.println("getResponse");
        Client2 instance = null;
        String expResult = "";
        String result = instance.getResponse();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResponse method, of class Client2.
     */
    @Test
    public void testSetResponse() {
        System.out.println("setResponse");
        String response = "";
        Client2 instance = null;
        instance.setResponse(response);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
