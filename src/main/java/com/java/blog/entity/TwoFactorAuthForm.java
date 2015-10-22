/* 
 *  COPYRIGHT NOTICE 
 *  This source code and any binary data compiled from it is Copyright (C) 2002 by
 *  Team Netsol Limited.  This source code and its compilations are protected by
 *  both the English copyright law and international copyright treaty
 *  provisions.
 *  
 *  DISCLAIMER 
 *  This source code is provided "AS IS". You agree that by using this source
 *  code or its compilations, you are expressly acknowledging that its use is
 *  AT YOUR OWN RISK. TEAM NETSOL LIMITED MAKES NO WARRANTY OF ANY KIND, EITHER
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO IMPLIED WARRANTIES OF
 *  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, WITH RESPECT TO THIS
 *  SOURCE CODE, ITS COMPILATIONS AND ANY ACCOMPANYING DOCUMENTATION. IN NO
 *  EVENT SHALL TEAM NETSOL LIMITED BE LIABLE FOR ANY DAMAGES (INCLUDING DAMAGES
 *  FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, LOSS OF BUSINESS
 *  INFORMATION, OR OTHER PECUNIARY LOSS) ARISING OUT OF THE USE OF OR INABILITY
 *  TO USE THIS SOURCE CODE OR ITS COMPILATIONS, EVEN IF TEAM NETSOL LIMITED HAS
 *  BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *  
 *  TRADEMARKS 
 *  Any product or brand names mentioned in this source code, its compilations
 *  and associated manuals and files are trademarks or registered trademarks of
 *  their respective owners.
 *  
 *  DISTRIBUTION
 *  This source code and its compilations may IN NO MANNER WHATSOEVER be
 *  transmitted, copied, sold, rented or by any other means distributed to ANY
 *  PARTY without the EXPRESS WRITTEN PERMISSION OF TEAM NETSOL LIMITED.
 *  
 *  (C) 2002 Team Netsol Limited. All Rights Reserved. 
 */

package com.java.blog.entity;

/*
 * Created By: semiribrahim
 * Created On: 22 Sep 2015 at 12:01:34
 */

public class TwoFactorAuthForm {

  private String secretKey;
  private String barCodeUrl;
  private Boolean twoFactorAuthInitialised;
  private Integer verificationCode;

  public TwoFactorAuthForm( User User ) {
    this.secretKey = User.getSecretKey( );
    this.twoFactorAuthInitialised = User.getTwoFactorAuthInitialised( );
  }

  public TwoFactorAuthForm( ) {

  }

  public String getSecretKey( ) {
    return secretKey;
  }

  public void setSecretKey( String secretKey ) {
    this.secretKey = secretKey;
  }

  public String getBarCodeUrl( ) {
    return barCodeUrl;
  }

  public void setBarCodeUrl( String barCodeUrl ) {
    this.barCodeUrl = barCodeUrl;
  }

  public Boolean getTwoFactorAuthInitialised( ) {
    return twoFactorAuthInitialised;
  }

  public void setTwoFactorAuthInitialised( Boolean twoFactorAuthInitialised ) {
    this.twoFactorAuthInitialised = twoFactorAuthInitialised;
  }

  public Integer getVerificationCode( ) {
    return verificationCode;
  }

  public void setVerificationCode( Integer verificationCode ) {
    this.verificationCode = verificationCode;
  }

}
