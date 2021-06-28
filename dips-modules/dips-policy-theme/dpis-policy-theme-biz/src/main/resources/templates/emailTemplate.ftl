<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div id="main" style="margin: 0 auto;">
        <div id="top">
          <div style="padding: 10px 0 0 10px;height: 55px;">
            <img src="http://gc.govmade.cn/img/email/logo.jpg" style="float: left;padding-top:5px;">
            <a id="website" href="http://gc.govmade.cn/" target="_Blank">
              <span>国策官网</span>
            </a>
          </div>
        </div>
        <div id="topPic">
          <img src="http://gc.govmade.cn/img/email/pic4.jpg">
          <div style="width:900px;height:2px;background:rgb(208, 22, 26);margin-top:15px;"></div>
        </div>
        <p style="color: rgb(65, 65, 65);width:900px;margin:38px auto 0;padding-left:55px;">
            <strong style="font-size: 15px;">尊敬的${userName?default('国策用户')} ：</strong>
          <span style="font-size: 14px;">欢迎使用国策数据，以下是为你推荐的最新政策，请点击查看。</span>
        </p>
        <div id="listOne">
          <p style="padding-top:15px;"><img src="http://gc.govmade.cn/img/email/tou.png"><span style="position: absolute;padding:0 0 0 4px;font-size: 14px;font-weight: bold;color: rgb(65, 65, 65);">订阅推荐</span></p>
          <div style="width: 670px;height:210px;float:left;">
             <#list emailContent as emailContent>
            <div class="listOneShow">
              <span class="listOneTri"></span>
              <a class="listOneTitle" href="${emailContent.url}" target="_Blank">${emailContent.title}</a>
            </div>
			      </#list>
          </div>
          <div style="float:left;width: 1px;height: 290px; background: darkgray;margin: 0 23px 0 23px;"></div>
          <div style="float:left;width: 160px;height:160px;text-align: center;margin-top:33px;">
            <img src="http://gc.govmade.cn/img/email/mini.jpg" style="width:100px;height:100px;margin-top:30px;">
            <div class="listOneQRTxt" style="margin-top:15px;">订阅推送</div>
            <div class="listOneQRTxt">小程序浏览新体验</div>
          </div>
        </div>
        <div id="listTwo">
          <p style="padding-top:15px;"><img src="http://gc.govmade.cn/img/email/tou.png"><span style="position: absolute;padding:0 0 0 4px;font-size: 14px;font-weight: bold;color: rgb(65, 65, 65);">更多资讯</span></p>
          <div>
            <div style="width:800px;height:150px;background: #fff;margin:0 auto;">
              <div class="listTwoShowOne" style="width:300px;height:100px;margin-left:50px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve1.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">个性化数据库定制</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/data-service/shared-database" target="_Blank">查看详情</a>
                </div>
              </div>
              <div class="listTwoShowOne" style="width: 1px;height: 100px; background: darkgray;margin:0 50px 0 50px;"></div>
              <div class="listTwoShowOne" style="width:300px;height:100px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve2.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">政策定制与精准推送</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/data-service/policy-subscription" target="_Blank">查看详情</a>
                </div>
              </div>
            </div>
            <div style="width:800px;height:150px;background: #fff;margin:0 auto;">
              <div class="listTwoShowOne" style="width:300px;height:100px;margin-left:50px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve3.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">多维度标杆分析</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/data-service/benchmarking" target="_Blank">查看详情</a>
                </div>
              </div>
              <div class="listTwoShowOne" style="width: 1px;height: 100px; background: darkgray;margin:0 50px 0 50px;"></div>
              <div class="listTwoShowOne" style="width:300px;height:100px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve4.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">官方授权各类数据库API</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/data-service/online-modeling" target="_Blank">查看详情</a>
                </div>
              </div>
            </div>
            <div style="width:800px;height:140px;background: #fff;margin:0 auto;">
              <div class="listTwoShowOne" style="width:300px;height:100px;margin-left:50px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve5.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">各类政策数据应用需求</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/policy-database/general-policy" target="_Blank">查看详情</a>
                </div>
              </div>
              <div class="listTwoShowOne" style="width: 1px;height: 100px; background: darkgray;margin:0 50px 0 50px;"></div>
              <div class="listTwoShowOne" style="width:300px;height:100px;">
                <img class="listTwoPic" src="http://gc.govmade.cn/img/email/serve6.jpg">
                <div class="listTwoTxt">
                  <div class="listTwoTxtShow">整合重要政策与客户</div>
                  <a class="listTwoBtn" size="mini" plain href="http://gc.govmade.cn/policy-database/policy-knowledge"  target="_Blank">查看详情</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div id="bottom">
          <p style="padding-top:15px;"><img src="http://gc.govmade.cn/img/email/tou.png"><span style="position: absolute;padding:0 0 0 4px;font-size: 14px;font-weight: bold;color: rgb(65, 65, 65);">关于我们</span></p>
          <div id="bottomOne">
            <div style="width: 390px;height:150px;float:left;">
              <div style="width:370px;margin:15px 0 0 25px;height:150px;">
                <div class="bottomQRShow">
                  <img class="bottomQR" src="http://gc.govmade.cn/img/email/wx.jpg">
                  <div class="bottomTxt">
                    <span class="bottomTxtShow">国策数据</span><br>
                    <span class="bottomTxtShow">官方微信服务号</span>
                  </div>
                </div>
                <div class="bottomQRShow">
                  <img class="bottomQR" src="http://gc.govmade.cn/img/email/wxh.jpg">
                  <div class="bottomTxt">
                    <span class="bottomTxtShow">国脉互联</span><br>
                    <span class="bottomTxtShow">微信公众号</span><br>
                  </div>
                </div>
                <div class="bottomQRShow">
                  <img class="bottomQR" src="http://gc.govmade.cn/img/email/gm.jpg">
                  <div class="bottomTxt">
                    <span class="bottomTxtShow">国脉集团官方频道</span><br>
                    <a class="bottomTxtClick" href="https://www.govmade.com/" target="_Blank">点击查看</a>
                  </div>
                </div>
              </div>
            </div>
            <div style="float:left;width: 1px;height: 150px; background: darkgray;"></div>
            <div style="float:left;width:500px;height:150px;">
              <p class="bottomRightTxt">此邮件为广告邮件，请勿直接回复。</p>
              <p class="bottomRightTxt">如有疑问或建议，欢迎随时联系我们。</p>
              <p class="bottomRightTxt">此邮件中内容版权归国脉互联有限公司所有，未经许可请勿转发、使用、编辑此邮件。</p>
              <p class="bottomRightTxt">Email: Club@govmade.com， 服务热线: 400-633-0170</p>
              <p class="bottomRightTxt">国脉互联版权所有©2014-2019</p>
            </div>
          </div><hr style="margin-top:180px;">
          <div id="bottomTwo" style="margin-top:25px;">
            <div style="width:680px;height:100px;margin: 0 auto;">
              <a class="bottomBtn" href="https://www.govmade.com/newsinformation/" target="_Blank">
                <img class="bottomBtnImg" src="http://gc.govmade.cn/img/email/at.png">
                <div class="bottomBtnTxt">
                  <span>新闻中心</span>
                </div>
              </a>
              <a class="bottomBtn" href="https://cp.govmade.com/" target="_Blank">
                <img class="bottomBtnImg" src="http://gc.govmade.cn/img/email/protect.png">
                <div class="bottomBtnTxt">
                  <span>产品中心</span>
                </div>
              </a>
              <a class="bottomBtn" href="https://www.govmade.com/business/" target="_Blank">
                <img class="bottomBtnImg" src="http://gc.govmade.cn/img/email/statement.png">
                <div class="bottomBtnTxt">
                  <span>业务体系</span>
                </div>
              </a>
              <a class="bottomBtn" href="https://www.govmade.com/govcus/" target="_Blank">
                <img class="bottomBtnImg" src="http://gc.govmade.cn/img/email/cancel.png">
                <div class="bottomBtnTxt">
                  <span>合作加盟</span>
                </div>
              </a>
              <a class="bottomBtn" href="tencent://Message/?Uin=2533385272&websiteName=www.besticity.com=&Menu=yes" target="_Blank">
                <img class="bottomBtnImg" src="http://gc.govmade.cn/img/email/support.png">
                <div class="bottomBtnTxt">
                  <span>服务支持</span>
                </div>
              </a>
            </div>
          </div>
        </div>
      </div>
</body>
</html>
<script>

</script>
<style>
#top{
  width:900px;
  margin: 0 auto;
  border-top: 4px solid rgb(208, 22, 26);
  border-bottom: 2px solid #eee;
}
#topPic{
  margin: 0 auto;
  width:900px;
  height: 300px;
  border-bottom: 2px solid #eee;
}
#listOne{
  width: 900px;
  margin: 0 auto;
  min-height: 350px;
  border: 1px solid #ddd;
  border-image: -webkit-linear-gradient(#fff,#ddd,#fff) 30 30;
  border-image: -moz-linear-gradient(#fff,#ddd,#fff) 30 30;
  border-image: linear-gradient(#fff,#ddd,#fff) 30 30;
}
#listTwo{
  width: 900px;
  margin: 20px auto 0;
  height: 520px;
  border: 1px solid #ddd;
  border-image: -webkit-linear-gradient(#fff,#ddd,#fff) 30 30;
  border-image: -moz-linear-gradient(#fff,#ddd,#fff) 30 30;
  border-image: linear-gradient(#fff,#ddd,#fff) 30 30;
}
#bottom{
  width: 900px;
  margin: 0 auto;
  height: 340px;
  background: rgb(241, 243, 244);
}
.bottomBtn{
  width: 100px;
  height: 80px;
  float: left;
  margin-left: 30px;
  background: #fff;
  border-radius: 10px;
  cursor: pointer;
}
.bottomQRShow{
  width: 100px;
  height: 150px;
  float: left;
  margin-right: 20px;
}
.bottomQR{
  width: 80px;
  height: 80px;
  padding-left: 10px;
}
.bottomTxt{
  width: 100px;
  text-align: center;
}
.bottomBtnImg{
  width: 30px;
  height: 30px;
  margin: 10px 35px 0 35px;
}
.bottomBtnTxt{
  width: 100px;
  text-align: center;
}
.bottomBtnTxt span{
  font-size: 14px;
  font-weight: bold;
  color: black;
}
.bottomTxtShow{
  font-size: 12px;
  font-weight: bold;
  color: rgb(65, 65, 65);
}
.bottomTxtClick{
  color: rgb(65, 61, 255);
  font-size: 11px;
  cursor: pointer;
}
.bottomRightTxt{
  font-size: 12px;
  padding-left: 30px;
}
.listTwoShowOne{
  float: left;
  background: rgb(245, 245, 245);
  cursor: pointer;
}
.listTwoPic{
  width: 100px;
  height: 100px;
  float: left;
}
.listTwoTxt{
  width: 180px;
  height: 100px;
  float: left;
}
.listTwoTxtShow{
  width: 180px;
  height: 45px;
  padding: 15px 0 0 25px;
  font-size: 15px;
  font-weight: bold;
  color: rgb(65, 65, 65);
}
.el-button{
  margin-left: 25px;
  background: rgb(208, 22, 26);
  color: #fff;
}
.listOneQRTxt{
  width: 160px;
  font-size: 13px;
  font-weight: bold;
  color: rgb(65, 65, 65);
}
.listOneTri{
  position: absolute;
  border-style: solid;
  border-color: #fff #4285f4 #fff #4285f4;
  -webkit-transform-origin: 50% center;
  transform-origin: 50% center;
  border-radius: 50%;
  border-width: 6px;
  margin: 6px 0 0 70px;
}
.listOneTitle{
  margin-left: 95px;
  color: rgb(65, 65, 65);
  text-decoration: none!important;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: block;
}
.listTwoBtn{
  margin: 0 45px;
  width: 48px;
  height: 20px;
  background: rgb(208, 22, 26);
  color: #fff;
  font-size: 12px;
  padding: 5px 20px;
  border-radius: 3px;
}
.listTwoBtn:hover{
  color: rgb(64, 158, 255);
  background: #fff;
  border: 1px solid rgb(64, 158, 255);
}
#website{
  float: right;
  margin:6px 23px;
  width: 52px;
  height: 19px;
  padding: 5px 20px;
  border-radius: 3px;
  background: rgb(208, 22, 26);
  color: #fff;
  font-size: 13px;
  cursor: pointer;
}
#website:hover{
  color: rgb(64, 158, 255);
  background: #fff;
  border: 1px solid rgb(64, 158, 255);
}
.listOneShow{
  width: 660px;
  margin-top:6px;
}
a{
  text-decoration: none!important;
  cursor: pointer;
}
</style>