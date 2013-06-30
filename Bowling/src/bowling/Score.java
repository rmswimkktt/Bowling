package bowling;


public class Score {
	
	//1から8フレーム用に使用可能なtargetフレームのスコアを計算する
	public Integer calcFrame1to8(
			int targetFirst, int targetSecond,
			int nextFirst, int nextSecond,
			int nextNextFirst) {
		//ストライク、スペア、その他の順番で振り分けていく
		//targetフレームがストライク
		if(targetFirst == Static.STRIKE_SCORE){
			//nextフレームがストライク
			if(nextFirst == Static.STRIKE_SCORE){
				return targetFirst + nextFirst + nextNextFirst;
			}
			return targetFirst + nextFirst + nextSecond;
		}
		//targetフレームがスペア
		if(targetFirst + targetSecond == Static.SPARE_SCORE){
			return targetFirst + targetSecond + nextFirst;
		}
		return targetFirst + targetSecond;
	}
	
	//9フレーム用のスコアを計算する
	public Integer calcFrame9(int targetFirst, int targetSecond, int nextFirst, int nextSecond) {
		if(targetFirst == Static.STRIKE_SCORE){
			return targetFirst + nextFirst + nextSecond;
		}
		if(targetFirst + targetSecond == Static.SPARE_SCORE){
			return targetFirst + targetSecond + nextFirst;
		}
		return targetFirst + targetSecond;
	}
	
	//10フレーム用のスコアを計算する
	public Integer calcFrame10(int targetFirst, int targetSecond, int targetThird) {
		return targetFirst + targetSecond + targetThird;
	}

	//char型で表されたゲームのスコアをint型に変換する
	public static int[] ConvertCharToIntScore(char[] gameScore) {
		int[] intScore = new int[Static.MAX_THROW_NUM];
		if(!(gameScore.length == Static.MAX_THROW_NUM)){
			return null;
		}
		for(int i = 0; i < gameScore.length; i++){
			char bitCharScore = gameScore[i];
			if(Static.FAUL.compareTo(String.valueOf(bitCharScore)) == 0 ||
				Static.GARTER.compareTo(String.valueOf(bitCharScore)) == 0 ||
				Static.NON_THROW.compareTo(String.valueOf(bitCharScore)) == 0){
				//ファールかガーターか投げていない場合
				intScore[i] = Static.GARTER_FAUL_SCORE;
			}
			else if(Static.STRIKE.compareTo(String.valueOf(bitCharScore)) == 0){
				//ストライクの場合
				intScore[i] = Static.STRIKE_SCORE;
			}
			else{
				intScore[i] = new Integer(String.valueOf(bitCharScore));
			}
		}
		return intScore;
	}

	//ゲームスコアから各フレームのスコアを計算する
	public static int[] calc(char[] gameScore) {
		//入力を数値に置き換え
		int[] intScore = ConvertCharToIntScore(gameScore);
		//数値の入力からスコアを計算する
		int[] intFrameScore = new int[Static.LAST_FRAME_NUM];
		Score score = new Score();
		for(int frameNum = Static.FIRST_FRAME_NUM; frameNum <= Static.LAST_FRAME_NUM; frameNum++){
			//求めるフレームまでに、スコアがいくつ着いているかを現す
			int alreadyThrowNum = (frameNum - 1) * 2;
			switch(frameNum){
			case 10 :
				intFrameScore[frameNum - 1] = intFrameScore[frameNum - 2] + 
					score.calcFrame10(
						intScore[alreadyThrowNum],
						intScore[alreadyThrowNum + 1],
						intScore[alreadyThrowNum + 2]);
				break;
			case 9:
				intFrameScore[frameNum - 1] = intFrameScore[frameNum - 2] + 
					score.calcFrame9(
						intScore[alreadyThrowNum],
						intScore[alreadyThrowNum + 1],
						intScore[alreadyThrowNum + 2],
						intScore[alreadyThrowNum + 3]);
				break;
			case 1:
				//1フレーム目はその前のフレームのスコアを加算する必要はない
				intFrameScore[frameNum - 1] = 
					score.calcFrame1to8(
						intScore[alreadyThrowNum],
						intScore[alreadyThrowNum + 1],
						intScore[alreadyThrowNum + 2],
						intScore[alreadyThrowNum + 3],
						intScore[alreadyThrowNum + 4]);
				break;
			default:
				intFrameScore[frameNum - 1] = intFrameScore[frameNum - 2] + 
					score.calcFrame1to8(
						intScore[alreadyThrowNum],
						intScore[alreadyThrowNum + 1],
						intScore[alreadyThrowNum + 2],
						intScore[alreadyThrowNum + 3],
						intScore[alreadyThrowNum + 4]);
				break;
			}
		}
		return intFrameScore;
	}
}
