package chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChatMemberTest {

    private ChatMember chatMember;
    private TestChatServer testServer;
    private TestSocket testSocket;

    @BeforeEach
    void setUp() throws Exception {
        testServer = new TestChatServer();
        testSocket = new TestSocket();
        chatMember = new ChatMember(testServer);
        chatMember.handleConnection(testSocket);
    }

    @Test
    void testHandleNameCommand() {
        chatMember.process("name:TestUser");
        assertEquals("TestUser just joined the chat", testServer.publishedMessages.get(0));
    }

    @Test
    void testHandleMessageCommand() {
        chatMember.process("name:TestUser");
        chatMember.process("msg:Hello everyone!");

        assertEquals(2, testServer.publishedMessages.size());
        assertEquals("TestUser: Hello everyone!", testServer.publishedMessages.get(1));
    }

    @Test
    void testHandleExitCommand() {
        chatMember.process("name:TestUser");
        chatMember.process("exit");

        assertEquals(2, testServer.publishedMessages.size());
        assertEquals("TestUser left the chat", testServer.publishedMessages.get(1));
        assertTrue(testServer.removedMembers.contains(chatMember));
    }

    private static class TestChatServer extends ChatServer {
        List<String> publishedMessages = new ArrayList<>();
        List<ChatMember> removedMembers = new ArrayList<>();

        @Override
        public void publish(String message) {
            publishedMessages.add(message);
        }

        @Override
        public void removeMember(ChatMember member) {
            removedMembers.add(member);
        }
    }

    // Stub class for Socket
    private static class TestSocket extends Socket {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        @Override
        public ByteArrayOutputStream getOutputStream() {
            return outputStream;
        }

        @Override
        public ByteArrayInputStream getInputStream() {
            return new ByteArrayInputStream(new byte[0]); // Empty input stream for testing
        }
    }
}
