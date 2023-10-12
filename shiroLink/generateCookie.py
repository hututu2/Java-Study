#!/usr/bin/python3
# -*- coding: utf-8 -*-
import logging
import base64
import uuid
import binascii
from Crypto.Cipher import AES
 
 
class AESCrypt:
    """
    AES/CBC/PKCS5Padding 加密
    """
    def __init__(self, key):
        """
        使用密钥,加密模式进行初始化
        :param key:
        """

 
        self.key = base64.b64decode(key)
        self.MODE = AES.MODE_CBC
        self.block_size = 16
 
        # 填充函数
        # self.padding = lambda data: data + (self.block_size - len(data) % self.block_size) * chr(self.block_size - len(data) % self.block_size)
        # 此处为一坑,需要现将data转换为byte再来做填充，否则中文特殊字符等会报错
        self.padding = lambda data: data + ((self.block_size - len(data) % self.block_size) * chr(self.block_size - len(data) % self.block_size)).encode()
        # 截断函数
        self.unpadding = lambda data: data[:-data[-1]]
 
    def aes_encrypt(self, plaintext):
        """
        加密
        :param plaintext: 明文
        :return:
        """
        try:
            # 填充16位
            padding_text = self.padding(plaintext)
            # 初始化加密器
            iv=uuid.uuid4().bytes
            cryptor = AES.new(self.key, self.MODE, iv)
            # 进行AES加密
            encrypt_aes = cryptor.encrypt(padding_text)
            # 进行BASE64转码
            encrypt_text = (base64.b64encode(iv+encrypt_aes)).decode()
            return encrypt_text
        except Exception as e:
            logging.exception(e)
 
    def aes_decrypt(self, ciphertext):
        """
        解密
        :param ciphertext: 密文
        :return:
        """
        try:
            # 密文必须是16byte的整数倍
            # if len(ciphertext) % 16 != 0:
            #     raise binascii.Error('密文错误!')
            # 进行BASE64转码
            plain_base64 = base64.b64decode(ciphertext)
            iv = plain_base64[:16]
            cryptor = AES.new(self.key, self.MODE, iv)
            
            # 进行ASE解密
            decrypt_text = cryptor.decrypt(plain_base64[16:])
            # 截取
            plain_text = self.unpadding(decrypt_text)
            return plain_text
        except UnicodeDecodeError as e:
            logging.error('解密失败,请检查密钥是否正确!')
            logging.exception(e)
        except binascii.Error as e:
            logging.exception(e)
        except Exception as e:
            logging.exception(e)
 
 
if __name__ == '__main__':
	# 测试
    cryptor = AESCrypt('kPH+bIxk5D2deZiIxcaaaA==')
    # aes_decrypt_str = cryptor.aes_decrypt('MyrqfysfI8JADXgBvZv5ZuH3yz8xgQZBPlsy2hG6h/fEb/mD+52cIJeTrkqXJB0uW5kmdJ1mB+Vl5jFkqp+O3sBYNsfF5PXUXHC1tcpJYbfZZnJMMRrYKcDSzsgZ8uqFQFsJuu7MZ+lz858A4NFshNaNApfKvx2DdtNUAu6AL/qDCpUsZHXg2qKVf+JLo2bGB4t9tws7MvbJkZsRPM+F+muwPnnni+HurU7ntfUK0olcIjcd32jhtOqLAgX86kENN6ZIW3Yl/xsOSyki2QRiufqs9DCB8+V/RLOUwPsGbkLUgCsRIQRsGRjGIwXZdhcSb5bWrx0WXeQgQ92TqgKPRK3R7cj/AV96YB+VP7JoFbHpvyH4Pi8TI7rJ7cYnDu9EZtt0l17VuYGvIpDWP53OmslUQ7vKdSI5M68OVZ8fhW9fsbW+7k0PrhS07fuRjCkETxpJveAujES3fsqfjYZF9IjCYv6EZsubvc0hxST4+xGVadDq4YBCd6A0izAjpSEj')
    # print(f'解密结果为: {aes_decrypt_str}')""
    with open('D:/ctf_tools/java_study/shiro/samples/shiroLink/cc.bin','rb') as f:
        aes_encrypt_str = cryptor.aes_encrypt(f.read())
        print(aes_encrypt_str)