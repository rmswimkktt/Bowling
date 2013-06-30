package bowling;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ScoreTest {
	@RunWith(Theories.class)
	public static class CalcFrameScore{
		@DataPoints
		public static Fixture1to8[] RAPAM1to8s = {
			new Fixture1to8(0, 0, 10, 0, 8, 0),
			new Fixture1to8(10, 0, 8, 2, 10, 20),
			new Fixture1to8(8, 2, 10, 0, 10, 20),
			new Fixture1to8(10, 0, 10, 0, 10, 30),
			new Fixture1to8(10, 0, 10, 0, 5, 25),
			new Fixture1to8(10, 0, 5, 3, 8, 18),
			new Fixture1to8(5, 3, 8, 2, 10, 8),
			new Fixture1to8(8, 2, 10, 0, 10, 20),
			new Fixture1to8(8, 1, 9, 0, 9, 9)
		};
		@DataPoints
		public static Fixture9[] RAPAM9s = {
			new Fixture9(10, 0, 10, 10, 30),
			new Fixture9(10, 0, 10, 0, 20),
			new Fixture9(10, 0, 0, 10, 20),
		};
		@DataPoints
		public static Fixture10[] RAPAM10s = {
			new Fixture10(10, 10, 10, 30)
		};
		
		@Theory
		public void 第1_8フレームが計算できるか(Fixture1to8 params) {
			Score score = new Score();
			int actual = score.calcFrame1to8(
					params.targetFirst,
					params.targetSecond,
					params.nextFirst,
					params.nextSecond,
					params.nextNextFirst);
			String msg = "input:" + params.toString();
			assertThat(msg, actual, is(params.expected));
		}
		@Theory
		public void 第9フレームが計算できるか(Fixture9 params) {
			Score score = new Score();
			int actual = score.calcFrame9(
					params.targetFirst,
					params.targetSecond,
					params.nextFirst,
					params.nextSecond);
			String msg = "input:" + params.toString();
			assertThat(msg, actual, is(params.expected));
		}
		@Theory
		public void 第10フレームが計算できるか(Fixture10 params) {
			Score score = new Score();
			int actual = score.calcFrame10(
					params.targetFirst,
					params.targetSecond,
					params.nextFirst);
			String msg = "input:" + params.toString();
			assertThat(msg, actual, is(params.expected));
		}
		static class Fixture1to8{
			int targetFirst;
			int targetSecond;
			int nextFirst;
			int nextSecond;
			int nextNextFirst;
			int expected;
			public Fixture1to8(int targetFirst, int targetSecond, int nextFirst,
					int nextSecond, int nextNextFirst, int expected) {
				super();
				this.targetFirst = targetFirst;
				this.targetSecond = targetSecond;
				this.nextFirst = nextFirst;
				this.nextSecond = nextSecond;
				this.nextNextFirst = nextNextFirst;
				this.expected = expected;
			}
			@Override
			public String toString() {
				return "[" + targetFirst + ",\t" + targetSecond + ",\t"
						+ nextFirst + ",\t" + nextSecond + ",\t"
						+ nextNextFirst + ",\t" + expected + "]";
			}
		}
		static class Fixture9{
			int targetFirst;
			int targetSecond;
			int nextFirst;
			int nextSecond;
			int expected;
			public Fixture9(int targetFirst, int targetSecond, int nextFirst,
					int nextSecond, int expected) {
				this.targetFirst = targetFirst;
				this.targetSecond = targetSecond;
				this.nextFirst = nextFirst;
				this.nextSecond = nextSecond;
				this.expected = expected;
			}
			@Override
			public String toString() {
				return "[" + targetFirst + ",\t" + targetSecond + ",\t"
						+ nextFirst + ",\t" + nextSecond + ",\t" + expected
						+ "]";
			}
			
		}
		static class Fixture10{
			int targetFirst;
			int targetSecond;
			int nextFirst;
			int nextSecond;
			int nextNextFirst;
			int expected;
			public Fixture10(int targetFirst, int targetSecond, int nextFirst,
					int expected) {
				this.targetFirst = targetFirst;
				this.targetSecond = targetSecond;
				this.nextFirst = nextFirst;
				this.expected = expected;
			}
			@Override
			public String toString() {
				return "[" + targetFirst + ",\t" + targetSecond + ",\t"
						+ nextFirst + ",\t" + nextSecond + ",\t"
						+ nextNextFirst + ",\t" + expected + "]";
			}
			
		}
	}
	
	public static class testInputCharToInt{
		char[] gameScore;
		@Before
		public void ゲームシナリオ1(){
			gameScore = new char[]{
					'G', 'F',			//1フレーム目
					'S', '-',			//2フレーム目
					'8', '2',			//3フレーム目
					'S', '-',			//4フレーム目
					'S', '-',			//5フレーム目
					'S', '-',			//6フレーム目
					'5', '3',			//7フレーム目
					'8', '2',			//8フレーム目
					'S', '-',			//9フレーム目
					'S', 'S', 'S'};		//10フレーム目
		}
		@Test
		public void charからintへの変換(){
			int[] expected = {
					0, 0,			//1フレーム目
					10, 0,			//2フレーム目
					8, 2,			//3フレーム目
					10, 0,			//4フレーム目
					10, 0,			//5フレーム目
					10, 0,			//6フレーム目
					5, 3,			//7フレーム目
					8, 2,			//8フレーム目
					10, 0,			//9フレーム目
					10, 10, 10};		//10フレーム目
			int[] actual = Score.ConvertCharToIntScore(gameScore);
			assertThat(actual, is(expected));
		}
		@Test
		public void ゲームのスコアを計算する1(){
			int[] expected = {0, 20, 40, 70, 95, 113, 121, 141, 171, 201};
			int[] actual = Score.calc(gameScore);
			assertThat(actual, is(expected));
		}
	}
}
