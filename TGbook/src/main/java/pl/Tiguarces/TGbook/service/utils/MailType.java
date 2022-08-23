package pl.Tiguarces.TGbook.service.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailType {
    ACTIVATION("""
        Hello %s, here is your activation link, click below: <br>
        <a href='%s' target="_blank"> Activate </a>
        """, "TGBook - Activate your account"),

    SEND_TO_CHANGE_EMAIL("""
          Hello %s, here is your new activation link, click below to change your old mail to new: <br>
          <a href='%s' target="_blank"> Activate </a>
          """, "TGBook - Activate to change your email");

    private final String message;
    private final String subject;
}