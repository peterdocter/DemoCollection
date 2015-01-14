package com.github.airk.democollection.model;

/**
 * Created by kevin on 15/1/14.
 */
public class FakeImageData {
    private static String[] allImages;

    public static String[] getAllImages() {
        return allImages;
    }

    static {
        allImages = new String[10];
        allImages[0] = "http://f.hiphotos.baidu.com/image/w%3D310/sign=dcd7ab7c0bfa513d51aa6adf0d6c554c/14ce36d3d539b60077ab3117ea50352ac65cb72c.jpg";
        allImages[1] = "http://a.hiphotos.baidu.com/image/w%3D310/sign=eab1912b0ff431adbcd245387b36ac0f/9825bc315c6034a806d9cc38c81349540923764b.jpg";
        allImages[2] = "http://h.hiphotos.baidu.com/image/w%3D310/sign=edcc752abc096b63811958513c328733/ac345982b2b7d0a2eb7eb698c9ef76094b369a04.jpg";
        allImages[3] = "http://b.hiphotos.baidu.com/image/w%3D310/sign=d0f38144b01bb0518f24b529067bda77/503d269759ee3d6d2c7f0aad40166d224f4ade1f.jpg";
        allImages[4] = "http://b.hiphotos.baidu.com/image/w%3D310/sign=d559e0c1f9edab6474724bc1c736af81/e824b899a9014c081f50b6af087b02087bf4f455.jpg";
        allImages[5] = "http://a.hiphotos.baidu.com/image/w%3D310/sign=f997d898b01bb0518f24b529067bda77/503d269759ee3d6d051b537140166d224e4adeaf.jpg";
        allImages[6] = "http://e.hiphotos.baidu.com/image/w%3D310/sign=d3c410d674c6a7efb926ae27cdfbafe9/fc1f4134970a304e11ca15f3d3c8a786c8175cca.jpg";
        allImages[7] = "http://e.hiphotos.baidu.com/image/w%3D310/sign=96c18988d4ca7bcb7d7bc12e8e086b3f/0dd7912397dda14449fbdcf9b1b7d0a20df4868f.jpg";
        allImages[8] = "http://b.hiphotos.baidu.com/image/w%3D310/sign=c6ad1d947e1ed21b79c928e49d6eddae/0b55b319ebc4b74502903b70ccfc1e178a82154b.jpg";
        allImages[9] = "http://d.hiphotos.baidu.com/image/w%3D310/sign=082d2e84cbea15ce41eee60886003a25/7aec54e736d12f2eb22eee614dc2d56285356883.jpg";
    }
}
