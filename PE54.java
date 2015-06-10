import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

/** Finds the number of hands that Player 1 wins. */
public class PE54 {

    public enum HandType {
        HIGH_CARD(0), ONE_PAIR(1), TWO_PAIRS(2), THREE_OF_A_KIND(3), STRAIGHT(4),
        FLUSH(5), FULL_HOUSE(6), FOUR_OF_A_KIND(7), STRAIGHT_FLUSH(8), ROYAL_FLUSH(9);

        HandType(int score) {
            this.score = score;
        }

        int score;

        /** Returns the hand that applies to CARDS. */
        public static HandType handType(Hand cards) {
            if (isRoyalFlush(cards)) {
                return ROYAL_FLUSH;
            } else if (isStraightFlush(cards)) {
                return STRAIGHT_FLUSH;
            } else if (isFourOfAKind(cards)) {
                return FOUR_OF_A_KIND;
            } else if (isFlush(cards)) {
                return FLUSH;
            } else if (isStraight(cards)) {
                return STRAIGHT;
            } else if (isThreeKind(cards)) {
                return THREE_OF_A_KIND;
            } else if (isPairs(cards, 2)) {
                return TWO_PAIRS;
            } else if (isPairs(cards, 1)) {
                return ONE_PAIR;
            }
            return HIGH_CARD;
        }

        /** Tests if HAND is a royal flush. */
        private static boolean isRoyalFlush(Hand cards) {
            List<String> v = cards.values;
            boolean hasValues = v.contains("T") && v.contains("J") &&
                v.contains("Q") && v.contains("K") && v.contains("A");
            if (!hasValues) {
                return false;
            }
            int maxSuit = 0;
            for (List<String> vals : cards.suits.values()) {
                maxSuit = (maxSuit < vals.size()) ? vals.size() : maxSuit;
            }
            return maxSuit == 5;
        }

        /** Tests if HAND is a straight flush. */
        private static boolean isStraightFlush(Hand cards) {
            return isStraight(cards) && isFlush(cards);
        }

        /** Test if HAND is four of a kind. */
        private static boolean isFourOfAKind(Hand cards) {
            Integer[] vals = new Integer[13];
            Arrays.fill(vals, new Integer(0));
            List<String> cio = Arrays.asList(CARDS_IN_ORDER.split(" "));
            for (String card : cards.valuesRep) {
                vals[cio.indexOf(card)] += 1;
            }
            List<Integer> t = Arrays.asList(vals);
            return t.contains(4);
        }

        /** Test if HAND is a full house. */
        private static boolean isFullHouse(Hand cards) {
            Integer[] vals = new Integer[13];
            Arrays.fill(vals, new Integer(0));
            List<String> cio = Arrays.asList(CARDS_IN_ORDER.split(" "));
            for (String card : cards.valuesRep) {
                vals[cio.indexOf(card)] += 1;
            }
            List<Integer> t = Arrays.asList(vals);
            return t.contains(3) && t.contains(2);
        }

        /** Test if HAND is a flush. */
        private static boolean isFlush(Hand cards) {
            int maxSuit = 0;
            for (List<String> vals : cards.suits.values()) {
                maxSuit = (maxSuit < vals.size()) ? vals.size() : maxSuit;
            }
            return maxSuit == 5;
        }

        /** Test if HAND is a straight. */
        private static boolean isStraight(Hand cards) {
            String v = String.join(" ", cards.values);
            String straight_cio = "2 3 4 5 A" + CARDS_IN_ORDER;
            boolean hasValues = straight_cio.contains(v) && cards.values.size() == 5;
            return hasValues;
        }

        /** Test if HAND is three of a kind. */
        private static boolean isThreeKind(Hand cards) {
            Integer[] vals = new Integer[13];
            Arrays.fill(vals, new Integer(0));
            List<String> cio = Arrays.asList(CARDS_IN_ORDER.split(" "));
            for (String card : cards.valuesRep) {
                vals[cio.indexOf(card)] += 1;
            }
            List<Integer> t = Arrays.asList(vals);
            return t.contains(3) && !t.contains(2);
        }

        /** Test if HAND has N pairs. */
        private static boolean isPairs(Hand cards, int n) {
            Integer[] vals = new Integer[13];
            Arrays.fill(vals, new Integer(0));
            List<String> cio = Arrays.asList(CARDS_IN_ORDER.split(" "));
            for (String card : cards.valuesRep) {
                vals[cio.indexOf(card)] += 1;
            }
            int numPairs = 0;
            for (int i : vals) {
                if (i == 2) numPairs++;
            }
            return numPairs == n;
        }
    }

    public static class Hand {
        public List<String> cards;
        public List<String> values;
        public List<String> valuesRep;
        public HashMap<String, List<String>> suits;
        public Hand(List<String> cards) {
            this.cards = cards;
            this.valuesRep = new ArrayList<String>();
            this.suits = new HashMap<String, List<String>>();
            this.suits.put("H", new ArrayList<String>());
            this.suits.put("S", new ArrayList<String>());
            this.suits.put("C", new ArrayList<String>());
            this.suits.put("D", new ArrayList<String>());
            for (String c : cards) {
                this.valuesRep.add(c.substring(0, 1));
                this.suits.get(c.substring(1)).add(c.substring(0, 1));
            }
            this.values = new ArrayList<String>(Arrays.asList(CARDS_IN_ORDER.split(" ")));
            this.values.retainAll(valuesRep);
        }

        public Hand(String cards) {
            this(Arrays.asList(cards.split(" ")));
        }

        /** Finds the max value of the pair or triple for H. Assumes that H
         *  contains a pair or triple or four of a kind. */
        public String maxValueFor(int n) {
            int pos = -1;
            Integer[] vals = new Integer[13];
            Arrays.fill(vals, new Integer(0));
            List<String> cio = Arrays.asList(CARDS_IN_ORDER.split(" "));
            for (String card : this.valuesRep) {
                vals[cio.indexOf(card)] += 1;
            }
            for (int i = 0; i < 13; i++) {
                if (vals[i] == n) {
                    pos = i;
                }
            }
            return cio.get(pos);
        }
    }

    public static String CARDS_IN_ORDER = "2 3 4 5 6 7 8 9 T J Q K A";

    /** Returns true if Player 1 wins, otherwise false. */
    public static boolean whoWins(String p1, String p2) {
        Hand h1 = new Hand(p1), h2 = new Hand(p2);
        HandType ht1 = HandType.handType(h1), ht2 = HandType.handType(h2);
        if (ht1.score != ht2.score) {
            return ht1.score > ht2.score;
        }
        return tiebreaker(h1, h2, ht1);
    }

    public static boolean whoWins(Hand h1, Hand h2) {
        HandType ht1 = HandType.handType(h1), ht2 = HandType.handType(h2);
        if (ht1.score != ht2.score) {
            return ht1.score > ht2.score;
        }
        return tiebreaker(h1, h2, ht1);
    }

    /** Given that H1 and H2 are the same HANDTYPE, breaks the tie and determines a winner. */
    public static boolean tiebreaker(Hand h1, Hand h2, HandType handtype) {
        if (handtype == HandType.HIGH_CARD || handtype == HandType.FLUSH) {
            int max1 = -1, max2 = -1;
            for (String v : h1.values) {
                max1 = Math.max(max1, CARDS_IN_ORDER.indexOf(v));
            }
            for (String v : h2.values) {
                max2 = Math.max(max2, CARDS_IN_ORDER.indexOf(v));
            }
            if (max1 != max2) {
                return max1 > max2;
            } else {
                h1.values.remove(CARDS_IN_ORDER.substring(max1, max1 + 1));
                h2.values.remove(CARDS_IN_ORDER.substring(max1, max1 + 1));
                return tiebreaker(h1, h2, handtype);
            }
        } else if (handtype == HandType.ONE_PAIR) {
            String s1 = h1.maxValueFor(2), s2 = h2.maxValueFor(2);
            int p1 = CARDS_IN_ORDER.indexOf(s1), p2 = CARDS_IN_ORDER.indexOf(s2);
            if (p1 != p2) {
                return p1 > p2;
            } else {
                h1.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
                h2.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
                return tiebreaker(h1, h2, HandType.HIGH_CARD);
            }
        } else if (handtype == HandType.TWO_PAIRS) {
            String s1 = h1.maxValueFor(2), s2 = h2.maxValueFor(2);
            int p1 = CARDS_IN_ORDER.indexOf(s1), p2 = CARDS_IN_ORDER.indexOf(s2);
            if (p1 != p2) {
                return p1 > p2;
            } else {
                h1.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
                h2.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
               h1.valuesRep.retainAll(h1.values);
                h2.valuesRep.retainAll(h2.values);
                return tiebreaker(h1, h2, HandType.ONE_PAIR);
            }
        } else if (handtype == HandType.THREE_OF_A_KIND || handtype == HandType.FULL_HOUSE) {
            String s1 = h1.maxValueFor(3), s2 = h2.maxValueFor(3);
            int p1 = CARDS_IN_ORDER.indexOf(s1), p2 = CARDS_IN_ORDER.indexOf(s2);
            if (p1 != p2) {
                return p1 > p2;
            } else {
                h1.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
                h2.values.remove(CARDS_IN_ORDER.substring(p1, p1 + 1));
                h1.valuesRep.retainAll(h1.values);
                h2.valuesRep.retainAll(h2.values);
                return tiebreaker(h1, h2, HandType.HIGH_CARD);
            }
        } else if (handtype == HandType.STRAIGHT || handtype == HandType.STRAIGHT_FLUSH) {
            String cioS = "A" + CARDS_IN_ORDER;
            String v1 = String.join(" ", h1.values), v2 = String.join(" ", h2.values);
            int p1 = cioS.indexOf(v1), p2 = cioS.indexOf(v2);
            return p1 > p2;
        } else if (handtype == HandType.FOUR_OF_A_KIND) {
            String s1 = h1.maxValueFor(4), s2 = h2.maxValueFor(4);
            int p1 = CARDS_IN_ORDER.indexOf(s1), p2 = CARDS_IN_ORDER.indexOf(s2);
            return p1 > p2;
        }
        return true;
    }

    /** Main program. */
	public static void main(String[] args) {
        File dir = new File("/media/frankgoji/Windows/Users/Frankgoji/Documents/personal_projects/project_euler/p054_poker.txt");
        Path file = dir.toPath();
        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
            int wins = 0;
            for (String line : lines) {
                List<String> cards = Arrays.asList(line.split(" "));
                Hand h1 = new Hand(cards.subList(0, 5)), h2 = new Hand(cards.subList(5, 10));
                if (whoWins(h1, h2)) wins++;
            }
            System.out.println(wins);
        } catch (IOException i) {
            // Nothing to do
            System.out.println("some io error");
        }
	}
}
