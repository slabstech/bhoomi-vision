FROM nvidia/cuda:11.7.0-devel-ubuntu22.04 as cuda_build
WORKDIR /app
RUN apt update && \
    apt install --no-install-recommends -y build-essential cmake gcc g++ && \
    apt clean && rm -rf /var/lib/apt/lists/* \
RUN export "CC=/usr/bin/gcc-10"
RUN export "CXX=/usr/bin/g++-10"
RUN export "CUDAHOSTCXX=/usr/bin/g++-10"

FROM cuda_build

COPY . .
CMD ["cmake", "--version"]