import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;

@RunWith(MockitoJUnitRunner.class)
public class UtilitiesTest {
    
    @Mock
    private DocumentBuilder documentBuilder;
    
    @Mock
    private Node node;
    
    @Test
    public void testParseXML() throws Exception {
        String xmlData = "<root><item>value</item></root>";
        
        Document document = mock(Document.class);
        when(documentBuilder.parse(any(InputSource.class))).thenReturn(document);
        
        Document result = Utilities.parseXML(xmlData);
        
        assertNotNull(result);
        verify(documentBuilder).parse(any(InputSource.class));
    }
    
    @Test
    public void testRemoveIrrelevantNodes() throws Exception {
        Node childNode1 = mock(Node.class);
        Node childNode2 = mock(Node.class);
        
        when(node.hasChildNodes()).thenReturn(true);
        when(node.getChildNodes()).thenReturn(mock(org.w3c.dom.NodeList.class));
        when(node.getChildNodes().getLength()).thenReturn(2);
        when(node.getChildNodes().item(0)).thenReturn(childNode1);
        when(node.getChildNodes().item(1)).thenReturn(childNode2);
        
        Utilities.removeIrrelevantNodes(node);
        
        verify(node).getChildNodes();
        verify(childNode1).getNodeType();
        verify(childNode2).getNodeType();
    }
    
    @Test
    public void testXmlToString() {
        String expected = "<root><item>value</item></root>";
        
        when(node.getNodeType()).thenReturn(Node.ELEMENT_NODE);
        when(node.hasChildNodes()).thenReturn(true);
        when(node.getChildNodes().getLength()).thenReturn(1);
        when(node.getFirstChild().getNodeType()).thenReturn(Node.TEXT_NODE);
        when(node.getFirstChild().getTextContent()).thenReturn("value");
        
        String result = Utilities.xmlToString(node);
        
        assertEquals(expected, result);
        verify(node).getNodeType();
        verify(node).hasChildNodes();
        verify(node).getChildNodes();
        verify(node.getFirstChild()).getNodeType();
        verify(node.getFirstChild()).getTextContent();
    }
}
