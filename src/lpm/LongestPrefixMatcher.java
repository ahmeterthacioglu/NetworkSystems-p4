/**
 * LongestPrefixMatcher.java
 *
 *   Version: 2019-07-10
 * Copyright University of Twente,  2015-2024
 *
 **************************************************************************
 *                          = Copyright notice =                          *
 *                                                                        *
 *            This file may ONLY  be distributed UNMODIFIED!              *
 * In particular, a correct solution to the challenge must  NOT be posted *
 * in public places, to preserve the learning effect for future students. *
 **************************************************************************
 */

package lpm;

import java.util.HashMap;
import java.util.Map;

public class LongestPrefixMatcher {

    /**
     * You can use this function to initialize variables.
     */
    private Map<Integer,Integer> cache = new HashMap<>();
    private TrieNode root;

    public LongestPrefixMatcher() {
        root = new TrieNode();
    }

    /**
     * Looks up an IP address in the routing tables.
     * @param ip The IP address to be looked up in integer representation
     * @return The port number this IP maps to
     */
    public int lookup(int ip) {
        Integer test = cache.get(ip);
        if(test != null){
            return test;
        }
        TrieNode node = root;
        int result = -1;

        for (int i = 31; i >= 0; i--) {
            int bit = (ip >> i) & 1;
            if (node.children[bit] != null) {
                node = node.children[bit];
                if (node.portNumber != -1) {
                    result = node.portNumber;
                }
            } else {
                break;
            }
        }
        cache.put(ip,result);
        return result;
    }

    /**
     * Adds a route to the routing tables.
     * @param ip The IP the block starts at in integer representation
     * @param prefixLength The number of bits indicating the network part
     *                     of the address range (notation ip/prefixLength)
     * @param portNumber The port number the IP block should route to
     */
    public void addRoute(int ip, byte prefixLength, int portNumber) {
        TrieNode node = root;
        for (int i = 31; i >= 32 - prefixLength; i--) {
            int bit = (ip >> i) & 1;
            if (node.children[bit] == null) {
                node.children[bit] = new TrieNode();
            }
            node = node.children[bit];
        }
        node.portNumber = portNumber;
    }

    /**
     * This method is called after all routes have been added.
     * You don't have to use this method but can use it to sort or otherwise
     * organize the routing information, if your datastructure requires this.
     */
    public void finalizeRoutes() {
        // TODO: Optionally do something
    }

    /**
     * Converts an integer representation IP to the human readable form.
     * @param ip The IP address to convert
     * @return The String representation for the IP (as xxx.xxx.xxx.xxx)
     */
    private String ipToHuman(int ip) {
        return Integer.toString(ip >> 24 & 0xff) + "." +
                Integer.toString(ip >> 16 & 0xff) + "." +
                Integer.toString(ip >> 8 & 0xff) + "." +
                Integer.toString(ip & 0xff);
    }

    /**
     * Parses an IP.
     * @param ipString The IP address to convert
     * @return The integer representation for the IP
     */
    private int parseIP(String ipString) {
        String[] ipParts = ipString.split("\\.");

        int ip = 0;
        for (int i = 0; i < 4; i++) {
            ip |= Integer.parseInt(ipParts[i]) << (24 - (8 * i));
        }

        return ip;
    }
    private static class TrieNode {
        private TrieNode[] children;
        private int portNumber;

        public TrieNode() {
            children = new TrieNode[2];
            portNumber = -1;
        }
    }
}
