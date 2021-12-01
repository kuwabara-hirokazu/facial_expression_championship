# AppDescription
### 表情選手権
 
画像から読み取った表情をスコア化して点数を競うアプリです。 
ランダムで出されるお題に対して表情スコア100点を目指しましょう。
 
 
# Android Architecture
 <img src="https://user-images.githubusercontent.com/72906152/133883479-79cbff46-5711-4d51-aebd-49c330559a19.png" width=80%>

# Library
* Dagger-hilt
* RxJava3
* Room
* Retrofit2
* CameraX
* Groupie
* Glide 　　etc.

# Usage
### FaceAPI使用手順  [仕様書](https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395236?utm_source=pocket_mylist)
* Microsoft Azureアカウントを作成し、FaceAPIの[APIキー]を取得する。(無料)
* `gradle.properties`ファイルに`faceApiKey=[APIキー]`を設定する。





# DEMO
### トップ画面
<img src="https://user-images.githubusercontent.com/72906152/133871157-6d28a228-51a1-4fc3-9e0f-21e99c6f4e9b.jpg" width=300dp>

### 対戦画面
|撮影|画像確認|
|----|----|
|<img src="https://user-images.githubusercontent.com/72906152/133871034-2b9a85a5-8edf-4fca-a36c-9c3c9c4beec5.jpg" width=300dp>|<img src="https://user-images.githubusercontent.com/72906152/133871170-c2523b90-a8d8-4cc5-a4c7-007a912cbaa1.jpg" width=300dp>|

|スコア結果|ランキング結果|
|----|----|
|<img src="https://user-images.githubusercontent.com/72906152/133871028-45a80b38-ccea-4318-84ce-7a77b5688fff.jpg" width=300dp>|<img src="https://user-images.githubusercontent.com/72906152/133871239-4078251e-114a-4c0b-a0dc-d77d45cb0a15.jpg" width=300dp>|

### 対戦履歴画面
|履歴一覧|履歴詳細|
|----|----|
|<img src="https://user-images.githubusercontent.com/72906152/133871258-6fe88942-90e0-42d3-b562-acd0a6f6fc29.jpg" width=300dp>|<img src="https://user-images.githubusercontent.com/72906152/133871209-cba00f8f-cd52-48b5-8e69-187bd14ce297.jpg" width=300dp>|


# Author
* Kuwabara Hirokazu
* [Twitter](https://twitter.com/kilalabu)
* [Qiita](https://qiita.com/kilalabu)






