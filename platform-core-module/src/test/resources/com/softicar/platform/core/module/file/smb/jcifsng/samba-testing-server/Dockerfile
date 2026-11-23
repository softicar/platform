# A simple Samba server for SMB tests, built locally by SambaTestingServer.
#
# Based on https://github.com/softicar/samba-testing-server (MIT License),
# which replaces the deprecated image "softicar/samba-testing-server".
#
# Share: testshare, user: testuser, password: testpassword, workgroup: WORKGROUP

FROM ubuntu:26.04

RUN	apt-get update \
	&& apt-get install -y --no-install-recommends \
		samba \
		smbclient \
		dumb-init \
	&& useradd --no-create-home --shell /usr/sbin/nologin testuser \
	&& (echo "testpassword"; echo "testpassword") | smbpasswd -s -a testuser \
	&& mkdir /testshare \
	&& chown testuser:testuser /testshare \
	&& rm /etc/samba/smb.conf \
	&& rm -rf /var/lib/apt/lists/*

COPY smb.conf /etc/samba/

EXPOSE 445

HEALTHCHECK --interval=50ms --retries=200 CMD smbclient -L localhost -U % -m SMB3

ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["/usr/sbin/smbd", "--foreground", "--debug-stdout", "--no-process-group"]
