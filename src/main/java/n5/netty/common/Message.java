package n5.netty.common;

import java.io.Serializable;

/**
 * @author haya
 */

public class Message implements Serializable {
    private static final long   serialVersionUID    = 1L;
    private String head;
    private String namespace;
    private String path;
    private String body;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder( "{" );
        sb.append( "\"head\":\"" )
                .append( head ).append( '\"' );
        sb.append( ",\"namespace\":\"" )
                .append( namespace ).append( '\"' );
        sb.append( ",\"path\":\"" )
                .append( path ).append( '\"' );
        sb.append( ",\"body\":\"" )
                .append( body ).append( '\"' );
        sb.append( '}' );
        return sb.toString();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
