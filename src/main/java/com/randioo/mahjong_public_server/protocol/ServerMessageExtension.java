// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: extension/ServerMessageExtension.proto

package com.randioo.mahjong_public_server.protocol;

public final class ServerMessageExtension {
  private ServerMessageExtension() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registry.add(com.randioo.mahjong_public_server.protocol.ServerMessageExtension.newProtocol);
  }
  public static final int NEWPROTOCOL_FIELD_NUMBER = 100;
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.randioo.mahjong_public_server.protocol.ServerMessage.SC,
      java.lang.Integer> newProtocol =
        com.google.protobuf.GeneratedMessage
          .newGeneratedExtension();
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&extension/ServerMessageExtension.proto" +
      "\022*com.randioo.mahjong_public_server.prot" +
      "ocol\032\023ServerMessage.proto:C\n\013newProtocol" +
      "\022..com.randioo.mahjong_public_server.pro" +
      "tocol.SC\030d \001(\005"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          com.randioo.mahjong_public_server.protocol.ServerMessageExtension.newProtocol.internalInit(
              com.randioo.mahjong_public_server.protocol.ServerMessageExtension.getDescriptor().getExtensions().get(0),
              java.lang.Integer.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.randioo.mahjong_public_server.protocol.ServerMessage.getDescriptor(),
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}