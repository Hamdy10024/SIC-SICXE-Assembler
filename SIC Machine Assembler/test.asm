TESTP    START   0x1000
         LDA     ALPHA,X
         ADD     BETRA
         STA     GAMMA
         RSUB    
STRIN    BYTE    0Xabcd
ALPHA    RESW    0x1
BETRA    RESW    1
GAMMA    RESW    1
         END     TESTP

